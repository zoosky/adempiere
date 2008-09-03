/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.apps;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;

import javax.servlet.ServletRequest;

import org.adempiere.webui.component.Window;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ALogin;
import org.compiere.db.CConnection;
import org.compiere.interfaces.Server;
import org.compiere.model.GridWindowVO;
import org.compiere.model.Lookup;
import org.compiere.model.MQuery;
import org.compiere.model.MRole;
import org.compiere.util.CCache;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.zkoss.web.servlet.Servlets;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 *  Windows Application Environment and utilities
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: AEnv.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 *
 *  Colin Rooney (croo) & kstan_79 RFE#1670185
 */
public final class AEnv
{
	/**
	 *  Show in the center of the screen.
	 *  (pack, set location and set visibility)
	 * 	@param window Window to position
	 */
	public static void showCenterScreen(Window window)
	{
		SessionManager.getAppDesktop().showWindow(window, "center");
	}   //  showCenterScreen

	/**
	 *	Position window in center of the screen
	 * 	@param window Window to position
	 */
	public static void positionCenterScreen(Window window)
	{
		showCenterScreen(window);
	}	//	positionCenterScreen

	/**
	 *  Show in the center of the screen.
	 *  (pack, set location and set visibility)
	 * 	@param window Window to position
	 * 	@param position 
	 */
	public static void showScreen(Window window, String position)
	{
		SessionManager.getAppDesktop().showWindow(window, position);
	}   //  showScreen

	/**
	 *	Position in center of the parent window.
	 *  (pack, set location and set visibility)
	 * 	@param parent Parent Window
	 * 	@param window Window to position
	 */
	public static void showCenterWindow(Window parent, Window window)
	{
		parent.appendChild(window);
		showScreen(window, "parent,center");
	}   //  showCenterWindow

	/**
	 *  Get Mnemonic character from text.
	 *  @param text text with '&'
	 *  @return Mnemonic or 0
	 */
	public static char getMnemonic (String text)
	{

		int pos = text.indexOf('&');
		if (pos != -1)					//	We have a nemonic
			return text.charAt(pos+1);
		return 0;

	}   //  getMnemonic


	/*************************************************************************
	 * 	Zoom
	 *	@param AD_Table_ID
	 *	@param Record_ID
	 */
	public static void zoom (int AD_Table_ID, int Record_ID)
	{
		String TableName = null;
		int AD_Window_ID = 0;
		int PO_Window_ID = 0;
		String sql = "SELECT TableName, AD_Window_ID, PO_Window_ID FROM AD_Table WHERE AD_Table_ID=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, AD_Table_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				TableName = rs.getString(1);
				AD_Window_ID = rs.getInt(2);
				PO_Window_ID = rs.getInt(3);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		//  Nothing to Zoom to
		if (TableName == null || AD_Window_ID == 0)
			return;

		//	PO Zoom ?
		boolean isSOTrx = true;
		if (PO_Window_ID != 0)
		{
			String whereClause = TableName + "_ID=" + Record_ID;
			isSOTrx = DB.isSOTrx(TableName, whereClause);
			if (!isSOTrx)
				AD_Window_ID = PO_Window_ID;
		}

		log.config(TableName + " - Record_ID=" + Record_ID + " (IsSOTrx=" + isSOTrx + ")");		
		zoom(AD_Window_ID, MQuery.getEqualQuery(TableName + "_ID", Record_ID));
	}	//	zoom

	/**
	 *	Exit System
	 *  @param status System exit status (usually 0 for no error)
	 */
	public static void exit (int status)
	{

		if (s_server != null)
		{
			try
			{
				s_server.remove();
			}
			catch (Exception ex)
			{
			}
		}
		Env.exitEnv(status);
	}	//	exit

	public static void logout()
	{
		if (s_server != null)
		{
			try
			{
				s_server.remove();
			}
			catch (Exception ex)
			{
			}
		}
		Env.logout();


	}

	/**
	 * 	Is Workflow Process view enabled.
	 *	@return true if enabled
	 */
	public static boolean isWorkflowProcess ()
	{

		if (s_workflow == null)
		{
			s_workflow = Boolean.FALSE;
			int AD_Table_ID = 645;	//	AD_WF_Process
			if (MRole.getDefault().isTableAccess (AD_Table_ID, true))	//	RO
				s_workflow = Boolean.TRUE;
			else
			{
				AD_Table_ID = 644;	//	AD_WF_Activity
				if (MRole.getDefault().isTableAccess (AD_Table_ID, true))	//	RO
					s_workflow = Boolean.TRUE;
				else
					log.config(s_workflow.toString());
			}
			//	Get Window
			if (s_workflow.booleanValue())
			{
				s_workflow_Window_ID = DB.getSQLValue (null,
					"SELECT AD_Window_ID FROM AD_Table WHERE AD_Table_ID=?", AD_Table_ID);
				if (s_workflow_Window_ID == 0)
					s_workflow_Window_ID = 297;	//	fallback HARDCODED
				//	s_workflow = Boolean.FALSE;
				log.config(s_workflow + ", Window=" + s_workflow_Window_ID);
			}
		}
		return s_workflow.booleanValue();

	}	//	isWorkflowProcess


	/**
	 * 	Start Workflow Process Window
	 *	@param AD_Table_ID optional table
	 *	@param Record_ID optional record
	 */
	public static void startWorkflowProcess (int AD_Table_ID, int Record_ID)
	{
		if (s_workflow_Window_ID == 0)
		{
			int AD_Window_ID = DB.getSQLValue(null, "SELECT AD_Window_ID FROM AD_Window WHERE Name = 'Workflow Process'");
			s_workflow_Window_ID = AD_Window_ID;
		}
		
		if (s_workflow_Window_ID == 0)
			return;
		
		MQuery query = new MQuery();
		query.addRestriction("AD_Table_ID", MQuery.EQUAL, AD_Table_ID);
		query.addRestriction("Record_ID", MQuery.EQUAL, Record_ID);
		AEnv.zoom(s_workflow_Window_ID, query);
	}	//	startWorkflowProcess


	/*************************************************************************/

	/** Workflow Menu		*/
	private static Boolean	s_workflow = null;
	/** Workflow Menu		*/
	private static int		s_workflow_Window_ID = 0;

	/**	Server Re-tries		*/
	private static int 		s_serverTries = 0;
	/**	Server Session		*/
	private static Server	s_server = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(AEnv.class);

	/**
	 *  Is AppsServer Active ?
	 *  @return true if active
	 */
	public static boolean isServerActive()
	{

		boolean contactAgain = s_server == null && s_serverTries == 0;
		boolean ok = CConnection.get().isAppsServerOK(contactAgain);
		if (ok)
		{
			s_serverTries = 0;
			return true;
		}
		if (s_serverTries > 1)	//	try twice
			return false;

		//	Try to connect
		CLogMgt.enable(false);
		try
		{
			s_serverTries++;
			log.config("try #" + s_serverTries);
			ok = CConnection.get().isAppsServerOK(true);
			if (ok)
				s_serverTries = 0;
		}
		catch (Exception ex)
		{
			ok = false;
			s_server = null;
		}
		CLogMgt.enable(true);
		//
		return ok;

	}   //  isServerActive

	/**
	 *  Get Server Version
	 *  @return Apps Server Version
	 *  @see ALogin#checkVersion
	 */
	public static String getServerVersion ()
	{
		return CConnection.get().getServerVersion();
	}   //  getServerVersion

	/**	Window Cache		*/
	private static CCache<Integer,GridWindowVO>	s_windows
		= new CCache<Integer,GridWindowVO>("AD_Window", 10);

	/**
	 *  Get Window Model
	 *
	 *  @param WindowNo  Window No
	 *  @param AD_Window_ID window
	 *  @param AD_Menu_ID menu
	 *  @return Model Window Value Obkect
	 */
	public static GridWindowVO getMWindowVO (int WindowNo, int AD_Window_ID, int AD_Menu_ID)
	{

		log.config("Window=" + WindowNo + ", AD_Window_ID=" + AD_Window_ID);
		GridWindowVO mWindowVO = null;
		if (AD_Window_ID != 0 && Ini.isCacheWindow())	//	try cache
		{
			mWindowVO = s_windows.get(AD_Window_ID);
			if (mWindowVO != null)
			{
				mWindowVO = mWindowVO.clone(WindowNo);
				log.info("Cached=" + mWindowVO);
			}
		}
		//  try to get from Server when enabled
		if (mWindowVO == null && DB.isRemoteObjects() && isServerActive())
		{
			log.config("trying server");
			try
			{
				s_server = CConnection.get().getServer();
				if (s_server != null)
				{
					mWindowVO = s_server.getWindowVO(Env.getCtx(), WindowNo, AD_Window_ID, AD_Menu_ID);
					log.config("from Server: success");
				}
			}
			catch (RemoteException e)
			{
				log.log(Level.SEVERE, "(RE)", e);
				mWindowVO = null;
				s_server = null;
			}
			catch (Exception e)
			{
				Throwable tt = e.getCause();
				if (tt != null && tt instanceof InvalidClassException)
					log.log(Level.SEVERE, "(Server<>Client class) " + tt);
				else if (tt != null && tt instanceof NotSerializableException)
					log.log(Level.SEVERE, "Serialization: " + tt.getMessage(), e);
				else
					log.log(Level.SEVERE, "ex", e);
				mWindowVO = null;
				s_server = null;
			}
			catch (Throwable t)
			{
				log.log(Level.SEVERE, t.toString());
				mWindowVO = null;
				s_server = null;
			}
			if (mWindowVO != null)
				s_windows.put(AD_Window_ID, mWindowVO);
		}	//	from Server

		//  Create Window Model on Client
		if (mWindowVO == null)
		{
			log.config("create local");
			mWindowVO = GridWindowVO.create (Env.getCtx(), WindowNo, AD_Window_ID, AD_Menu_ID);
			if (mWindowVO != null)
				s_windows.put(AD_Window_ID, mWindowVO);
		}	//	from Client
		if (mWindowVO == null)
			return null;

		//  Check (remote) context
		if (!mWindowVO.ctx.equals(Env.getCtx()))
		{
			//  Remote Context is called by value, not reference
			//  Add Window properties to context
			Enumeration<Object> keyEnum = mWindowVO.ctx.keys();
			while (keyEnum.hasMoreElements())
			{
				String key = (String)keyEnum.nextElement();
				if (key.startsWith(WindowNo+"|"))
				{
					String value = mWindowVO.ctx.getProperty (key);
					Env.setContext(Env.getCtx(), key, value);
				}
			}
			//  Sync Context
			mWindowVO.setCtx(Env.getCtx());
		}
		return mWindowVO;

	}   //  getWindow

	/**
	 *  Post Immediate
	 *  @param  WindowNo 		window
	 *  @param  AD_Table_ID     Table ID of Document
	 *  @param  AD_Client_ID    Client ID of Document
	 *  @param  Record_ID       Record ID of this document
	 *  @param  force           force posting
	 *  @return null if success, otherwise error
	 */
	public static String postImmediate (int WindowNo, int AD_Client_ID,
		int AD_Table_ID, int Record_ID, boolean force)
	{

		log.config("Window=" + WindowNo
			+ ", AD_Table_ID=" + AD_Table_ID + "/" + Record_ID
			+ ", Force=" + force);

		String error = null;
		//  try to get from Server when enabled
		if (isServerActive())
		{
			log.config("trying server");
			try
			{
				s_server = CConnection.get().getServer();
				if (s_server != null)
				{
					error = s_server.postImmediate(Env.getCtx(), AD_Client_ID,
						AD_Table_ID, Record_ID, force, null);
					log.config("from Server: " + error== null ? "OK" : error);
				}
				else
				{
					FDialog.error(WindowNo, "", "NoApps Server");
					return "NoAppsServer";
				}
			}
			catch (RemoteException e)
			{
				log.log(Level.WARNING, "(RE)", e);
				error = e.getMessage();
				s_server = null;
			}
			catch (Exception e)
			{
				log.log(Level.WARNING, "ex", e);
				error = e.getMessage();
				s_server = null;
			}
		}
		else
		{
			FDialog.error(WindowNo, "", "NoAppsServer");
			return "NoAppsServer";
		}
		return error;

	}   //  postImmediate

	/**
	 *  Cache Reset
	 *  @param  tableName	table name
	 *  @param  Record_ID	record id
	 */
	public static void cacheReset (String tableName, int Record_ID)
	{

		log.config("TableName=" + tableName + ", Record_ID=" + Record_ID);

		//  try to get from Server when enabled
		if (isServerActive())
		{
			log.config("trying server");
			try
			{
				Server server = CConnection.get().getServer();
				if (server != null)
				{
					server.cacheReset(tableName, Record_ID);
				}
			}
			catch (RemoteException e)
			{
				log.log(Level.SEVERE, "(RE)", e);
				s_server = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, "ex", e);
				s_server = null;
			}
		}

	}   //  cacheReset

	/**
	 *  Validate permissions to access Info queries on the view menu
	*   @author kstan_79
	*   @return true if access is allowed
	*/

	public static boolean canAccessInfo(String infoWindowName)
	{
		boolean result=false;
		int roleid= Env.getAD_Role_ID(Env.getCtx());
		String sqlRolePermission="Select COUNT(AD_ROLE_ID) AS ROWCOUNT FROM AD_ROLE WHERE AD_ROLE_ID=" + roleid
	                              + " AND ALLOW_INFO_" + infoWindowName + "='Y'";

		log.config(sqlRolePermission);
		PreparedStatement prolestmt = null;
		try
		{
			prolestmt = DB.prepareStatement (sqlRolePermission, null);

			ResultSet rs = prolestmt.executeQuery ();

			rs.next();

			if (rs.getInt("ROWCOUNT")>0)
			{
				result=true;
			}
			else
			{
				return false;
			}

			rs.close ();
			prolestmt.close ();
			prolestmt = null;
		}
		catch (Exception e)
			{
				System.out.println(e);
				log.log(Level.SEVERE, "(1)", e);
			}

		try
		{
			if (prolestmt != null)
			{
				prolestmt.close ();
			}
			prolestmt = null;
		}
		catch (Exception e)
		{
			prolestmt = null;
		}

		return result;

	} // 	canAccessInfo

    public static void actionRefresh(Lookup lookup, Object value, boolean mandatory)
    {
        if (lookup == null)
            return;

        lookup.refresh();
        if (lookup.isValidated())
            lookup.fillComboBox(mandatory, false, false, false);
        else
            lookup.fillComboBox(mandatory, true, false, false);
    }
    /**
     *
     * @param lookup
     * @param value
     */
    public static void actionZoom(Lookup lookup, Object value)
    {
        if (lookup == null)
            return;
        //
        MQuery zoomQuery = lookup.getZoomQuery();

        //  If not already exist or exact value
        if (zoomQuery == null || value != null)
        {
            zoomQuery = new MQuery();   //  ColumnName might be changed in MTab.validateQuery
            zoomQuery.addRestriction(lookup.getColumnName(), MQuery.EQUAL, value);
            zoomQuery.setRecordCount(1);    //  guess
        }
        int windowId = lookup.getZoom(zoomQuery);
        zoom(windowId, zoomQuery);
    }

	/**
	 * Zoom to a window with the provided window id and filters according to the
	 * query
	 * @param AD_Window_ID Window on which to zoom
	 * @param query Filter to be applied on the records.
	 */
	public static void zoom(int AD_Window_ID, MQuery query)
	{
		SessionManager.getAppDesktop().showZoomWindow(AD_Window_ID, query);
	}

	public static void showWindow(Window win)
	{
		SessionManager.getAppDesktop().showWindow(win);
	}

	/**
	 * 	Zoom
	 *	@param query query
	 */
	public static void zoom (MQuery query)
	{
		if (query == null || query.getTableName() == null || query.getTableName().length() == 0)
			return;
		String TableName = query.getTableName();
		int AD_Window_ID = 0;
		int PO_Window_ID = 0;
		String sql = "SELECT AD_Window_ID, PO_Window_ID FROM AD_Table WHERE TableName=?";
		try
		{
			PreparedStatement pstmt = DB.prepareStatement(sql, null);
			pstmt.setString(1, TableName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
			{
				AD_Window_ID = rs.getInt(1);
				PO_Window_ID = rs.getInt(2);
			}
			rs.close();
			pstmt.close();
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql, e);
		}
		//  Nothing to Zoom to
		if (AD_Window_ID == 0)
			return;

		//	PO Zoom ?
		boolean isSOTrx = true;
		if (PO_Window_ID != 0)
		{
			isSOTrx = DB.isSOTrx(TableName, query.getWhereClause(false));
			if (!isSOTrx)
				AD_Window_ID = PO_Window_ID;
		}

		log.config(query + " (IsSOTrx=" + isSOTrx + ")");

		zoom(AD_Window_ID, query);
	}


	/**
	 *  Get ImageIcon.
	 *
	 *  @param fileNameInImageDir full file name in imgaes folder (e.g. Bean16.png)
	 *  @return image
	 */
    public static URI getImage(String fileNameInImageDir)
    {
        URI uri = null;
        try
        {
            uri = new URI("/images/" + fileNameInImageDir);
        }
        catch (URISyntaxException exception)
        {
            log.log(Level.SEVERE, "Not found: " +  fileNameInImageDir);
            return null;
        }
        return uri;
    }   //  getImageIcon
    
    /**
     * 
     * @return boolean
     */
    public static boolean isFirefox2() {
    	Execution execution = Executions.getCurrent();
    	if (execution == null)
    		return false;
    	
    	Object n = execution.getNativeRequest();
    	if (n instanceof ServletRequest) {
    		String userAgent = Servlets.getUserAgent((ServletRequest) n);
    		return userAgent.indexOf("Firefox/2") >= 0;
    	} else {
    		return false;
    	}
    }
    
    /**
     * 
     * @param parent
     * @param child
     * @return boolean
     */
    public static boolean contains(Component parent, Component child) {
    	if (child == parent)
    		return true;
    	
    	Component c = child.getParent();
    	while (c != null) {
    		if (c == parent)
    			return true;
    		c = c.getParent();
    	}
    	
    	return false;
    }
    
    /**
     * 
     * @param pdfList
     * @param outFile
     * @throws IOException
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public static void mergePdf(List<File> pdfList, File outFile) throws IOException,
			DocumentException, FileNotFoundException {
		Document document = null;
		PdfWriter copy = null;					
		for (File f : pdfList) 
		{
			PdfReader reader = new PdfReader(f.getAbsolutePath());
			if (document == null)
			{
				document = new Document(reader.getPageSizeWithRotation(1));							
				copy = PdfWriter.getInstance(document, new FileOutputStream(outFile));
				document.open();
			}
			int pages = reader.getNumberOfPages();
			PdfContentByte cb = copy.getDirectContent();
			for (int i = 1; i <= pages; i++) {
				document.newPage();
				PdfImportedPage page = copy.getImportedPage(reader, i);
				cb.addTemplate(page, 0, 0);
			}
		}
		document.close();
    }
}	//	AEnv
