/**
 *  Product: Posterita Web-Based POS, Webstore and Adempiere Plugin
 *  Copyright (C) 2007  Posterita Ltd
 *  This file is part of POSterita
 *  
 *  POSterita is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package org.compiere.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;

import org.compiere.util.DB;

/**
 * Connection that is used to execute query on Server for Client processes
 * Need for Jasper Report processes as the Jasper Manager uses a connection
 *  
 * @author Ashley G Ramdass 
 */
public class ServerConnection implements Connection
{

	public void clearWarnings() throws SQLException
	{
		throw new java.lang.UnsupportedOperationException ("Method clearWarnings() not yet implemented.");
	}

	public void close() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method close() not yet implemented.");
	}

	public void commit() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method commit() not yet implemented.");
	}

	public Statement createStatement() throws SQLException
	{
		return DB.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return DB.createStatement(resultSetType, resultSetConcurrency, null);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return createStatement(resultSetType, resultSetConcurrency);
	}

	public boolean getAutoCommit() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getAutoCommit() not yet implemented.");
	}

	public String getCatalog() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getCatalog() not yet implemented.");
	}

	public int getHoldability() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getHoldability() not yet implemented.");
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getMetaData() not yet implemented.");
	}

	public int getTransactionIsolation() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getTransactionIsolation() not yet implemented.");
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getTypeMap() not yet implemented.");
	}

	public SQLWarning getWarnings() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method getWarnings() not yet implemented.");
	}

	public boolean isClosed() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method isClosed() not yet implemented.");
	}

	public boolean isReadOnly() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method isReadOnly() not yet implemented.");
	}

	public String nativeSQL(String sql) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method nativeSQL() not yet implemented.");
	}

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return DB.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return DB.prepareCall(sql, resultSetConcurrency, null);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return DB.prepareCall(sql, resultSetConcurrency, null);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return DB.prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method prepareStatement() not yet implemented.");
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method prepareStatement() not yet implemented.");
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method prepareStatement() not yet implemented.");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException
	{
		return DB.prepareStatement(sql, resultSetConcurrency, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method releaseSavepoint() not yet implemented.");
	}

	public void rollback() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method rollback() not yet implemented.");
	}

	public void rollback(Savepoint savepoint) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method rollback() not yet implemented.");
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setAutoCommit() not yet implemented.");
	}

	public void setCatalog(String catalog) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setCatalog() not yet implemented.");
	}

	public void setHoldability(int holdability) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setHoldability() not yet implemented.");
	}

	public void setReadOnly(boolean readOnly) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setReadOnly() not yet implemented.");
	}

	public Savepoint setSavepoint() throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setSavepoint() not yet implemented.");
	}

	public Savepoint setSavepoint(String name) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setSavepoint() not yet implemented.");
	}

	public void setTransactionIsolation(int level) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setTransactionIsolation() not yet implemented.");
	}

	public void setTypeMap(Map<String, Class<?>> arg0) throws SQLException
	{
	    throw new java.lang.UnsupportedOperationException ("Method setTypeMap() not yet implemented.");
	}

}
