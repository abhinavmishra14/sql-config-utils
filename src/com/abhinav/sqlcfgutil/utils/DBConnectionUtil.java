/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2016. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abhinav.sqlcfgutil.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class DBConnectionUtil.
 */
public final class DBConnectionUtil {
	
	private DBConnectionUtil(){
		super();
	}
	
	/**
	 * Gets the connection.
	 *
	 * @param driverName the driver name
	 * @param driverUrl the driver url
	 * @param userName the user name
	 * @param password the password
	 * @return the connection
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 */
	public static Connection getConnection(final String driverName,final String driverUrl,
			final String userName, final String password) throws ClassNotFoundException,
			SQLException {
		Class.forName(driverName);
		return DriverManager.getConnection(driverUrl, userName, password);
	}
	
	/**
	 * Close connection.
	 *
	 * @param conn the conn
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final Connection conn) throws SQLException {
		closeConnection(conn, null, null);
	}

	/**
	 * Close connection.
	 *
	 * @param stmt the stmt
	 * @param rset the rset
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final Statement stmt,final ResultSet rset)
			throws SQLException {
		closeConnection(null, stmt, rset);
	}

	/**
	 * Close connection.
	 *
	 * @param conn the conn
	 * @param stmt the stmt
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final Connection conn, final Statement stmt)
			throws SQLException {
		closeConnection(conn, stmt, null);
	}
	
	/**
	 * Close connection.
	 *
	 * @param stmt the stmt
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final Statement stmt)
			throws SQLException {
		closeConnection(null, stmt, null);
	}
	
	/**
	 * Close connection.
	 *
	 * @param rset the rs
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final ResultSet rset)
			throws SQLException {
		closeConnection(null, null, rset);
	}
	
	/**
	 * Close connection.
	 *
	 * @param conn the conn
	 * @param stmt the stmt
	 * @param rset the rset
	 * @throws SQLException the sQL exception
	 */
	public static void closeConnection(final Connection conn, final Statement stmt,
			final ResultSet rset) throws SQLException {
		if (conn != null){
			conn.close();
		}
		if (rset != null){
			rset.close();
		}
		if (stmt != null){
			stmt.close();
		}
	}
}
