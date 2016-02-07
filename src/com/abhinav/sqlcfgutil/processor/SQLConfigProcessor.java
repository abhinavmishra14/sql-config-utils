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
package com.abhinav.sqlcfgutil.processor;

import com.abhinav.sqlcfgutil.services.MySQLConfigService;
import com.abhinav.sqlcfgutil.utils.ConfigConstants;
import com.abhinav.sqlcfgutil.utils.ConfigUtils;

/**
 * The Class SQLConfigProcessor.
 */
public class SQLConfigProcessor {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("[SQLConfigProcessor:] SQLConfigProcessor reading the input data...");

		try {
			String cfgPath = args.length > 0 ? args[0] : ConfigConstants.EMPTY;
			final String userName = args.length > 1 ? args[1] : ConfigConstants.EMPTY;
			final String password = args.length > 2 ? args[2] : ConfigConstants.EMPTY;
			final String dbUrl = args.length > 3 ? args[3] : ConfigConstants.EMPTY;
			final String dbVendor = args.length > 4 ? args[4] : ConfigConstants.MYSQL_DBVENDOR;
			final String dbName = args.length > 5 ? args[5] : ConfigConstants.EMPTY;

			if (!ConfigConstants.EMPTY.equals(cfgPath) && cfgPath != null
					&& cfgPath.endsWith(ConfigConstants.XML)) {
				cfgPath = ConfigUtils.convertToLinuxFormat(cfgPath);
				System.out.println("[SQLConfigProcessor:] DB Config file : "+cfgPath);
			} else if(cfgPath!=null && !cfgPath.endsWith(ConfigConstants.XML)){
				throw new IllegalArgumentException("Invalid config file>> "+cfgPath);
			}
			else {
				throw new IllegalArgumentException("Please provide the db config file as a 1st agrument!");
			}
				
			if(userName.equals(ConfigConstants.EMPTY)){
				throw new IllegalArgumentException("Please provide the userName as a 2nd agrument!");
			}
			if(password.equals(ConfigConstants.EMPTY)){
				throw new IllegalArgumentException("Please provide the password as a 3rd agrument!");
			}
			if(dbUrl.equals(ConfigConstants.EMPTY)){
				throw new IllegalArgumentException("Please provide the database url as a 4th agrument!");
			}

			
			if (ConfigConstants.MSSQL_DBVENDOR.equals(dbVendor)) {
				processMSSQLRequest(dbName, cfgPath, userName, password,
						dbUrl.concat(ConfigConstants.FILE_SEPERATOR_LINUX).concat(ConfigConstants.MS_SQL_DBNAME_PREFIX).concat(dbName));
			} else {
				processMySQLRequest(dbName, cfgPath, userName, password,
						dbUrl.concat(ConfigConstants.FILE_SEPERATOR_LINUX).concat(dbName));
			}
		} catch (Exception excp) {
			System.out.println("[SQLConfigProcessor:] "+ excp.getMessage());
			excp.printStackTrace();
		}
	}

	/**
	 * Process my sql request.
	 *
	 * @param dbName the db name
	 * @param cfgPath the cfg path
	 * @param userName the user name
	 * @param password the password
	 * @param dbUrl the database url
	 * @throws Exception the exception
	 */
	public static void processMySQLRequest(final String dbName,final String cfgPath,
			final String userName,final String password,final String dbUrl) throws Exception {
		// Process MySql request
		final MySQLConfigService mysqlExec = new MySQLConfigService(cfgPath,
				dbName, userName, password, dbUrl);
		mysqlExec.executeStatements();
	}
	
	/**
	 * Process mssql request.
	 *
	 * @param dbName the db name
	 * @param cfgPath the cfg path
	 * @param userName the user name
	 * @param password the password
	 * @param dbUrl the database url
	 * @throws Exception the exception
	 */
	public static void processMSSQLRequest(final String dbName,final String cfgPath,
			final String userName,final String password,final String dbUrl) throws Exception {
		// Process MySql request
		final MySQLConfigService mysqlExec = new MySQLConfigService(cfgPath,
				dbName, userName, password, dbUrl);
		mysqlExec.executeStatements();
	}
}
