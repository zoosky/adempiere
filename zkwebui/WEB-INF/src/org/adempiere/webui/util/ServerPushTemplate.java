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
package org.adempiere.webui.util;

import java.util.logging.Level;

import org.compiere.util.CLogger;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;

/**
 * Zk UI update must be done in either UI thread or using server push. This class help to implement
 * that base on spring's jdbctemplate pattern.
 * @author hengsin
 *
 */
public class ServerPushTemplate {

	private Desktop desktop;
	
	private final static CLogger logger = CLogger.getCLogger(ServerPushTemplate.class);

	/**
	 * 
	 * @param desktop
	 */
	public ServerPushTemplate(Desktop desktop) {
		this.desktop = desktop;
	}
	
	/**
	 * Execute callback in UI thread
	 * @param callback
	 */
	public void execute(IServerPushCallback callback) {
		boolean inUIThread = Executions.getCurrent() != null;
		
		try {
	    	if (!inUIThread) {
	    		//1 second timeout
	    		Executions.activate(desktop, 1000);
	    	}
			callback.updateUI();
    	} catch (Exception e) {
    		logger.log(Level.WARNING, "Server push error="+e.getLocalizedMessage(), e);
    	} finally {
    		if (!inUIThread) {
    			Executions.deactivate(desktop);
    		}
    	}
	}
	
	/**
	 * 
	 * @return desktop
	 */
	public Desktop getDesktop() {
		return desktop;
	}
}
