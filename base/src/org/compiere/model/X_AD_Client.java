/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_Client
 *  @author Adempiere (generated) 
 *  @version Release 3.3.1t - $Id$ */
public class X_AD_Client extends PO implements I_AD_Client, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, int AD_Client_ID, String trxName)
    {
      super (ctx, AD_Client_ID, trxName);
      /** if (AD_Client_ID == 0)
        {
			setAutoArchive (null);
// N
			setIsCostImmediate (false);
// N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false);
// N
			setIsServerEMail (false);
			setIsSmtpAuthorization (false);
// N
			setIsUseBetaFunctions (true);
// Y
			setMMPolicy (null);
// F
			setName (null);
			setStoreArchiveOnFileSystem (false);
			setStoreAttachmentsOnFileSystem (false);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Client (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_Client[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AD_Language AD_Reference_ID=327 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	/** Set Language.
		@param AD_Language 
		Language for this entity
	  */
	public void setAD_Language (String AD_Language)
	{

		if (AD_Language != null && AD_Language.length() > 6)
		{
			log.warning("Length > 6 - truncated");
			AD_Language = AD_Language.substring(0, 6);
		}
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language () 
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** AutoArchive AD_Reference_ID=334 */
	public static final int AUTOARCHIVE_AD_Reference_ID=334;
	/** None = N */
	public static final String AUTOARCHIVE_None = "N";
	/** All (Reports, Documents) = 1 */
	public static final String AUTOARCHIVE_AllReportsDocuments = "1";
	/** Documents = 2 */
	public static final String AUTOARCHIVE_Documents = "2";
	/** External Documents = 3 */
	public static final String AUTOARCHIVE_ExternalDocuments = "3";
	/** Set Auto Archive.
		@param AutoArchive 
		Enable and level of automatic Archive of documents
	  */
	public void setAutoArchive (String AutoArchive)
	{
		if (AutoArchive == null) throw new IllegalArgumentException ("AutoArchive is mandatory");
		if (AutoArchive.equals("N") || AutoArchive.equals("1") || AutoArchive.equals("2") || AutoArchive.equals("3")); else throw new IllegalArgumentException ("AutoArchive Invalid value - " + AutoArchive + " - Reference_ID=334 - N - 1 - 2 - 3");
		if (AutoArchive.length() > 1)
		{
			log.warning("Length > 1 - truncated");
			AutoArchive = AutoArchive.substring(0, 1);
		}
		set_Value (COLUMNNAME_AutoArchive, AutoArchive);
	}

	/** Get Auto Archive.
		@return Enable and level of automatic Archive of documents
	  */
	public String getAutoArchive () 
	{
		return (String)get_Value(COLUMNNAME_AutoArchive);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{

		if (Description != null && Description.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Document Directory.
		@param DocumentDir 
		Directory for documents from the application server
	  */
	public void setDocumentDir (String DocumentDir)
	{

		if (DocumentDir != null && DocumentDir.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			DocumentDir = DocumentDir.substring(0, 60);
		}
		set_Value (COLUMNNAME_DocumentDir, DocumentDir);
	}

	/** Get Document Directory.
		@return Directory for documents from the application server
	  */
	public String getDocumentDir () 
	{
		return (String)get_Value(COLUMNNAME_DocumentDir);
	}

	/** Set EMail Test.
		@param EMailTest 
		Test EMail
	  */
	public void setEMailTest (String EMailTest)
	{

		if (EMailTest != null && EMailTest.length() > 1)
		{
			log.warning("Length > 1 - truncated");
			EMailTest = EMailTest.substring(0, 1);
		}
		set_Value (COLUMNNAME_EMailTest, EMailTest);
	}

	/** Get EMail Test.
		@return Test EMail
	  */
	public String getEMailTest () 
	{
		return (String)get_Value(COLUMNNAME_EMailTest);
	}

	/** Set Cost Immediately.
		@param IsCostImmediate 
		Update Costs immediately for testing
	  */
	public void setIsCostImmediate (boolean IsCostImmediate)
	{
		set_Value (COLUMNNAME_IsCostImmediate, Boolean.valueOf(IsCostImmediate));
	}

	/** Get Cost Immediately.
		@return Update Costs immediately for testing
	  */
	public boolean isCostImmediate () 
	{
		Object oo = get_Value(COLUMNNAME_IsCostImmediate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multi Lingual Documents.
		@param IsMultiLingualDocument 
		Documents are Multi Lingual
	  */
	public void setIsMultiLingualDocument (boolean IsMultiLingualDocument)
	{
		set_Value (COLUMNNAME_IsMultiLingualDocument, Boolean.valueOf(IsMultiLingualDocument));
	}

	/** Get Multi Lingual Documents.
		@return Documents are Multi Lingual
	  */
	public boolean isMultiLingualDocument () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiLingualDocument);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Post Immediately.
		@param IsPostImmediate 
		Post the accounting immediately for testing
	  */
	public void setIsPostImmediate (boolean IsPostImmediate)
	{
		set_Value (COLUMNNAME_IsPostImmediate, Boolean.valueOf(IsPostImmediate));
	}

	/** Get Post Immediately.
		@return Post the accounting immediately for testing
	  */
	public boolean isPostImmediate () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostImmediate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Server EMail.
		@param IsServerEMail 
		Send EMail from Server
	  */
	public void setIsServerEMail (boolean IsServerEMail)
	{
		set_Value (COLUMNNAME_IsServerEMail, Boolean.valueOf(IsServerEMail));
	}

	/** Get Server EMail.
		@return Send EMail from Server
	  */
	public boolean isServerEMail () 
	{
		Object oo = get_Value(COLUMNNAME_IsServerEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SMTP Authentication.
		@param IsSmtpAuthorization 
		Your mail server requires Authentication
	  */
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, Boolean.valueOf(IsSmtpAuthorization));
	}

	/** Get SMTP Authentication.
		@return Your mail server requires Authentication
	  */
	public boolean isSmtpAuthorization () 
	{
		Object oo = get_Value(COLUMNNAME_IsSmtpAuthorization);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Use Beta Functions.
		@param IsUseBetaFunctions 
		Enable the use of Beta Functionality
	  */
	public void setIsUseBetaFunctions (boolean IsUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsUseBetaFunctions, Boolean.valueOf(IsUseBetaFunctions));
	}

	/** Get Use Beta Functions.
		@return Enable the use of Beta Functionality
	  */
	public boolean isUseBetaFunctions () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBetaFunctions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** MMPolicy AD_Reference_ID=335 */
	public static final int MMPOLICY_AD_Reference_ID=335;
	/** LiFo = L */
	public static final String MMPOLICY_LiFo = "L";
	/** FiFo = F */
	public static final String MMPOLICY_FiFo = "F";
	/** Set Material Policy.
		@param MMPolicy 
		Material Movement Policy
	  */
	public void setMMPolicy (String MMPolicy)
	{
		if (MMPolicy == null) throw new IllegalArgumentException ("MMPolicy is mandatory");
		if (MMPolicy.equals("L") || MMPolicy.equals("F")); else throw new IllegalArgumentException ("MMPolicy Invalid value - " + MMPolicy + " - Reference_ID=335 - L - F");
		if (MMPolicy.length() > 1)
		{
			log.warning("Length > 1 - truncated");
			MMPolicy = MMPolicy.substring(0, 1);
		}
		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	/** Get Material Policy.
		@return Material Movement Policy
	  */
	public String getMMPolicy () 
	{
		return (String)get_Value(COLUMNNAME_MMPolicy);
	}

	/** Set Model Validation Classes.
		@param ModelValidationClasses 
		List of data model validation classes separated by ;
	  */
	public void setModelValidationClasses (String ModelValidationClasses)
	{

		if (ModelValidationClasses != null && ModelValidationClasses.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			ModelValidationClasses = ModelValidationClasses.substring(0, 255);
		}
		set_Value (COLUMNNAME_ModelValidationClasses, ModelValidationClasses);
	}

	/** Get Model Validation Classes.
		@return List of data model validation classes separated by ;
	  */
	public String getModelValidationClasses () 
	{
		return (String)get_Value(COLUMNNAME_ModelValidationClasses);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		if (Name == null)
			throw new IllegalArgumentException ("Name is mandatory.");

		if (Name.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			Name = Name.substring(0, 60);
		}
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Request EMail.
		@param RequestEMail 
		EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	  */
	public void setRequestEMail (String RequestEMail)
	{

		if (RequestEMail != null && RequestEMail.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			RequestEMail = RequestEMail.substring(0, 60);
		}
		set_Value (COLUMNNAME_RequestEMail, RequestEMail);
	}

	/** Get Request EMail.
		@return EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	  */
	public String getRequestEMail () 
	{
		return (String)get_Value(COLUMNNAME_RequestEMail);
	}

	/** Set Request Folder.
		@param RequestFolder 
		EMail folder to process incoming emails; if empty INBOX is used
	  */
	public void setRequestFolder (String RequestFolder)
	{

		if (RequestFolder != null && RequestFolder.length() > 20)
		{
			log.warning("Length > 20 - truncated");
			RequestFolder = RequestFolder.substring(0, 20);
		}
		set_Value (COLUMNNAME_RequestFolder, RequestFolder);
	}

	/** Get Request Folder.
		@return EMail folder to process incoming emails; if empty INBOX is used
	  */
	public String getRequestFolder () 
	{
		return (String)get_Value(COLUMNNAME_RequestFolder);
	}

	/** Set Request User.
		@param RequestUser 
		User Name (ID) of the email owner
	  */
	public void setRequestUser (String RequestUser)
	{

		if (RequestUser != null && RequestUser.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			RequestUser = RequestUser.substring(0, 60);
		}
		set_Value (COLUMNNAME_RequestUser, RequestUser);
	}

	/** Get Request User.
		@return User Name (ID) of the email owner
	  */
	public String getRequestUser () 
	{
		return (String)get_Value(COLUMNNAME_RequestUser);
	}

	/** Set Request User Password.
		@param RequestUserPW 
		Password of the user name (ID) for mail processing
	  */
	public void setRequestUserPW (String RequestUserPW)
	{

		if (RequestUserPW != null && RequestUserPW.length() > 20)
		{
			log.warning("Length > 20 - truncated");
			RequestUserPW = RequestUserPW.substring(0, 20);
		}
		set_Value (COLUMNNAME_RequestUserPW, RequestUserPW);
	}

	/** Get Request User Password.
		@return Password of the user name (ID) for mail processing
	  */
	public String getRequestUserPW () 
	{
		return (String)get_Value(COLUMNNAME_RequestUserPW);
	}

	/** Set Mail Host.
		@param SMTPHost 
		Hostname of Mail Server for SMTP and IMAP
	  */
	public void setSMTPHost (String SMTPHost)
	{

		if (SMTPHost != null && SMTPHost.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			SMTPHost = SMTPHost.substring(0, 60);
		}
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	/** Get Mail Host.
		@return Hostname of Mail Server for SMTP and IMAP
	  */
	public String getSMTPHost () 
	{
		return (String)get_Value(COLUMNNAME_SMTPHost);
	}

	/** Set Store Archive On File System.
		@param StoreArchiveOnFileSystem Store Archive On File System	  */
	public void setStoreArchiveOnFileSystem (boolean StoreArchiveOnFileSystem)
	{
		set_Value (COLUMNNAME_StoreArchiveOnFileSystem, Boolean.valueOf(StoreArchiveOnFileSystem));
	}

	/** Get Store Archive On File System.
		@return Store Archive On File System	  */
	public boolean isStoreArchiveOnFileSystem () 
	{
		Object oo = get_Value(COLUMNNAME_StoreArchiveOnFileSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Store Attachments On File System.
		@param StoreAttachmentsOnFileSystem Store Attachments On File System	  */
	public void setStoreAttachmentsOnFileSystem (boolean StoreAttachmentsOnFileSystem)
	{
		set_Value (COLUMNNAME_StoreAttachmentsOnFileSystem, Boolean.valueOf(StoreAttachmentsOnFileSystem));
	}

	/** Get Store Attachments On File System.
		@return Store Attachments On File System	  */
	public boolean isStoreAttachmentsOnFileSystem () 
	{
		Object oo = get_Value(COLUMNNAME_StoreAttachmentsOnFileSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Unix Archive Path.
		@param UnixArchivePath Unix Archive Path	  */
	public void setUnixArchivePath (String UnixArchivePath)
	{

		if (UnixArchivePath != null && UnixArchivePath.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			UnixArchivePath = UnixArchivePath.substring(0, 255);
		}
		set_Value (COLUMNNAME_UnixArchivePath, UnixArchivePath);
	}

	/** Get Unix Archive Path.
		@return Unix Archive Path	  */
	public String getUnixArchivePath () 
	{
		return (String)get_Value(COLUMNNAME_UnixArchivePath);
	}

	/** Set Unix Attachment Path.
		@param UnixAttachmentPath Unix Attachment Path	  */
	public void setUnixAttachmentPath (String UnixAttachmentPath)
	{

		if (UnixAttachmentPath != null && UnixAttachmentPath.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			UnixAttachmentPath = UnixAttachmentPath.substring(0, 255);
		}
		set_Value (COLUMNNAME_UnixAttachmentPath, UnixAttachmentPath);
	}

	/** Get Unix Attachment Path.
		@return Unix Attachment Path	  */
	public String getUnixAttachmentPath () 
	{
		return (String)get_Value(COLUMNNAME_UnixAttachmentPath);
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		if (Value == null)
			throw new IllegalArgumentException ("Value is mandatory.");

		if (Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			Value = Value.substring(0, 40);
		}
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Windows Archive Path.
		@param WindowsArchivePath Windows Archive Path	  */
	public void setWindowsArchivePath (String WindowsArchivePath)
	{

		if (WindowsArchivePath != null && WindowsArchivePath.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			WindowsArchivePath = WindowsArchivePath.substring(0, 255);
		}
		set_Value (COLUMNNAME_WindowsArchivePath, WindowsArchivePath);
	}

	/** Get Windows Archive Path.
		@return Windows Archive Path	  */
	public String getWindowsArchivePath () 
	{
		return (String)get_Value(COLUMNNAME_WindowsArchivePath);
	}

	/** Set Windows Attachment Path.
		@param WindowsAttachmentPath Windows Attachment Path	  */
	public void setWindowsAttachmentPath (String WindowsAttachmentPath)
	{

		if (WindowsAttachmentPath != null && WindowsAttachmentPath.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			WindowsAttachmentPath = WindowsAttachmentPath.substring(0, 255);
		}
		set_Value (COLUMNNAME_WindowsAttachmentPath, WindowsAttachmentPath);
	}

	/** Get Windows Attachment Path.
		@return Windows Attachment Path	  */
	public String getWindowsAttachmentPath () 
	{
		return (String)get_Value(COLUMNNAME_WindowsAttachmentPath);
	}
}