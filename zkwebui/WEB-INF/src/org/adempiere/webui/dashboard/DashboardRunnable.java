/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.dashboard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.adempiere.webui.desktop.IDesktop;
import org.adempiere.webui.session.ServerContext;
import org.adempiere.webui.session.SessionContextListener;
import org.adempiere.webui.util.ServerPushTemplate;
import org.compiere.model.MSysConfig;
import org.compiere.util.CLogger;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Desktop;

/**
 *
 * @author hengsin
 * @author Cristina Ghita, www.arhipac.ro BF [2871741] Error at start
 * @see https://sourceforge.net/tracker/?func=detail&atid=955896&aid=2871741&group_id=176962
 */
public class DashboardRunnable implements Runnable, Serializable
{
	
	private static final long serialVersionUID = 5995227773511788894L;
	
	private Desktop desktop;
	private boolean stop = false;
	private List<DashboardPanel> dashboardPanels;
	private IDesktop appDesktop;
	private Locale locale;

	@SuppressWarnings("unused")
	private static final CLogger logger = CLogger.getCLogger(DashboardRunnable.class);

	private final static String ZK_DASHBOARD_REFRESH_INTERVAL = "ZK_DASHBOARD_REFRESH_INTERVAL";

	/**
	 *
	 * @param desktop zk desktop interface
	 * @param appDesktop adempiere desktop interface
	 */
	public DashboardRunnable(Desktop desktop, IDesktop appDesktop) {
		this.desktop = desktop;
		this.appDesktop = appDesktop;

		dashboardPanels = new ArrayList<DashboardPanel>();
		locale = Locales.getCurrent();
	}

	public void run()
	{
		// default Update every one minutes
		int interval = MSysConfig.getIntValue(ZK_DASHBOARD_REFRESH_INTERVAL, 60000);
		while(!stop) {
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e1) {
				if (stop) break;
			}

			if (desktop.isAlive()) {
				Locales.setThreadLocal(locale);
				refreshDashboard();
			} else {
				break;
			}
		}
	}

	/**
	 * Refresh dashboard content
	 */
	public void refreshDashboard()
	{

		ServerPushTemplate template = new ServerPushTemplate(desktop);
    	for(int i = 0; i < dashboardPanels.size(); i++)
    	{
    		//make sure context is correct
    		Properties ctx = (Properties)template.getDesktop().getSession().getAttribute(SessionContextListener.SESSION_CTX);
    		if (ctx != null)
    		{
    			ServerContext serverContext = ServerContext.getCurrentInstance();
    			if (serverContext == null) {
    				serverContext = ServerContext.newInstance();	        	
    				serverContext.putAll(ctx);
    			} else {
    				String id = ctx.getProperty(SessionContextListener.SERVLET_SESSION_ID);
    				if (id == null || !id.equals(serverContext.getProperty(SessionContextListener.SERVLET_SESSION_ID))) {
    					serverContext.clear();
    					serverContext.putAll(ctx);
    				}
    			}
    		}
    		dashboardPanels.get(i).refresh(template);
    	}

    	//make sure context is correct
    	Properties ctx = (Properties)template.getDesktop().getSession().getAttribute(SessionContextListener.SESSION_CTX);
    	if (ctx != null)
    	{
    		ServerContext serverContext = ServerContext.getCurrentInstance();
    		if (serverContext == null) {
    			serverContext = ServerContext.newInstance();	        	
    			serverContext.putAll(ctx);
    		} else {
    			String id = ctx.getProperty(SessionContextListener.SERVLET_SESSION_ID);
				if (id == null || !id.equals(serverContext.getProperty(SessionContextListener.SERVLET_SESSION_ID))) {
					serverContext.clear();
					serverContext.putAll(ctx);
				}
    		}
    	}
    	appDesktop.onServerPush(template);
	}

	public void stop() {
		stop = true;
	}

	/**
	 * Add DashboardPanel to the auto refresh list
	 * @param dashboardPanel
	 */
	public void add(DashboardPanel dashboardPanel) {
		dashboardPanels.add(dashboardPanel);
	}
}
