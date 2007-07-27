/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *
 * Copyright (C) 2005 Robert Klein. robeklein@hotmail.com
 * Contributor(s): Low Heng Sin hengsin@avantz.com
 *****************************************************************************/
package org.adempiere.pipo.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import javax.xml.transform.sax.TransformerHandler;

import org.adempiere.pipo.AbstractElementHandler;
import org.adempiere.pipo.Element;
import org.compiere.model.X_AD_Package_Exp_Detail;
import org.compiere.model.X_AD_Val_Rule;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

public class DynValRuleElementHandler extends AbstractElementHandler {

	public void startElement(Properties ctx, Element element) throws SAXException {
		String elementValue = element.getElementValue();
		Attributes atts = element.attributes;
		log.info(elementValue+" "+atts.getValue("Name"));
		String entitytype = atts.getValue("EntityType");
		if (isProcessElement(ctx, entitytype)) {
			String name = atts.getValue("Name");
			int id = get_IDWithColumn(ctx, "AD_Val_Rule", "name", name);
			
			X_AD_Val_Rule m_ValRule = new X_AD_Val_Rule(ctx, id, getTrxName(ctx));
			int AD_Backup_ID = -1;
			String Object_Status = null;
			if (id > 0){		
				AD_Backup_ID = copyRecord(ctx, "AD_Val_Rule",m_ValRule);
				Object_Status = "Update";			
			}
			else{
				Object_Status = "New";
				AD_Backup_ID =0;
			}    	    
			m_ValRule.setDescription(atts.getValue("Description").replaceAll("'","''"));
			m_ValRule.setEntityType(atts.getValue("EntityType"));
			m_ValRule.setIsActive(atts.getValue("isActive") != null ? Boolean.valueOf(atts.getValue("isActive")).booleanValue():true);
			m_ValRule.setName(name);
			m_ValRule.setType(atts.getValue("Type"));		        
			m_ValRule.setCode(atts.getValue("Code"));		        
			if (m_ValRule.save(getTrxName(ctx)) == true){		    	
				record_log (ctx, 1, m_ValRule.getName(),"Task", m_ValRule.get_ID(),AD_Backup_ID, Object_Status,"AD_Val_Rule",get_IDWithColumn(ctx, "AD_Val_Rule", "Name", "AD_Val_Rule"));           		        		
			}
			else{
				record_log (ctx, 0, m_ValRule.getName(),"Task", m_ValRule.get_ID(),AD_Backup_ID, Object_Status,"AD_Val_Rule",get_IDWithColumn(ctx, "AD_Val_Rule", "Name", "AD_Val_Rule"));
			}
		} else {
			element.skip = true;
		}

	}

	public void endElement(Properties ctx, Element element) throws SAXException {
	}

	public void create(Properties ctx, TransformerHandler document)
			throws SAXException {
		int AD_Val_Rule_ID = Env.getContextAsInt(ctx, X_AD_Package_Exp_Detail.COLUMNNAME_AD_Val_Rule_ID);
		String sql = "SELECT Name FROM AD_Val_Rule WHERE  AD_Val_Rule_ID= " + AD_Val_Rule_ID;
		AttributesImpl atts = new AttributesImpl();
		PreparedStatement pstmt = null;
		pstmt = DB.prepareStatement (sql, getTrxName(ctx));		

		try {

			ResultSet rs = pstmt.executeQuery();		

			while (rs.next())
			{
				X_AD_Val_Rule m_ValRule = new X_AD_Val_Rule (ctx, AD_Val_Rule_ID, null);										
				createDynamicValidationRuleBinding(atts,m_ValRule);	
				document.startElement("","","dynvalrule",atts);
				document.endElement("","","dynvalrule");
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}

		catch (Exception e){
			log.log(Level.SEVERE,"getProcess", e);
		}
		finally{
			try	{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e){}
			pstmt = null;
		}
		
	}

	private AttributesImpl createDynamicValidationRuleBinding( AttributesImpl atts, X_AD_Val_Rule m_ValRule) 
	{
		atts.clear();
		atts.addAttribute("","","Name","CDATA",(m_ValRule.getName () != null ? m_ValRule.getName ():""));        
		//FIXME:  may not need this I guess
		//atts.addAttribute("","","AccessLevel","CDATA",(m_ValRule.getAccessLevel () != null ? m_ValRule.getAccessLevel ():""));
		atts.addAttribute("","","Code","CDATA",(m_ValRule.getCode() != null ? m_ValRule.getCode ():""));
		atts.addAttribute("","","Description","CDATA",(m_ValRule.getDescription () != null ? m_ValRule.getDescription ():""));
		atts.addAttribute("","","EntityType","CDATA",(m_ValRule.getEntityType () != null ? m_ValRule.getEntityType ():""));
		atts.addAttribute("","","Type","CDATA",(m_ValRule.getType () != null ? m_ValRule.getType ():""));
		atts.addAttribute("","","isActive","CDATA",(m_ValRule.isActive()== true ? "true":"false"));
		return atts;
	}
}
