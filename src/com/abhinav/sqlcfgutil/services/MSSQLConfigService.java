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
package com.abhinav.sqlcfgutil.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.abhinav.sqlcfgutil.model.DBConfigModel;
import com.abhinav.sqlcfgutil.utils.ConfigConstants;
import com.abhinav.sqlcfgutil.utils.DBConnectionUtil;
import com.abhinav.sqlcfgutil.utils.XMLParser;

/**
 * The Class MSSQLConfigService.
 */
public class MSSQLConfigService {

	/** The config map. */
	private Map<String, DBConfigModel> configMap;
	
	/** The db name. */
	private String dbName;
	
	/** The user name. */
	private String userName;
	
	/** The password. */
	private String password;
	
	/** The url. */
	private String url;

	
     /**
      * Instantiates a new my sql config processor.
      *
      * @param cfgPath the cfg path
      * @param dbName the db name
      * @param userName the user name
      * @param password the password
      * @param url the url
      * @throws Exception the exception
      */
     public MSSQLConfigService(final String cfgPath,final String dbName, final String userName,
    		 final String password, final String url) throws Exception {
		super();
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.url = url;
		
		/** Read the rules files and parse the same */
		final File cfg = new File(cfgPath);
		final FileInputStream fin = new FileInputStream(cfg);
		final byte[] xmlFileContent = new byte[(int) cfg.length()];
		fin.read(xmlFileContent);
		final InputStream xmlInputStream = new ByteArrayInputStream(xmlFileContent);
		final InputSource inputSource = new InputSource(xmlInputStream);
		final SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		saxFactory.setValidating(false);

		final XMLReader reader = saxFactory.newSAXParser().getXMLReader();
		final XMLParser xmlFilter = new XMLParser();
		xmlFilter.setParent(reader);
		xmlFilter.parse(inputSource);
		configMap = xmlFilter.getConfigMap();
		fin.close();
	}


	/**
	 * Execute statements.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws SQLException the sQL exception
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void executeStatements() throws ClassNotFoundException, SQLException, IllegalArgumentException {
		DBConfigModel dbCfgMdl = null;
		List<String> queries = null;
		Connection conn = null;
		PreparedStatement preStmt = null;
		int response = 0;
		if (dbName != null && !ConfigConstants.EMPTY.equals(dbName)) {
			System.out.println("[MSSQLConfigService:] Processing request for database name:- "
					+ dbName);
			try{
				dbCfgMdl = configMap.get(dbName);
				if (dbCfgMdl == null) {
					throw new IllegalArgumentException("Please provide the valid database name as per db configuration xml!");
				}
				System.out.println("DBConfiguration with following details: "+dbCfgMdl);
				conn = DBConnectionUtil.getConnection(ConfigConstants.MSSQLDRIVER,url,
						userName,password);
				queries=dbCfgMdl.getStatements().get(dbName);
				for (Iterator<String> iterator = queries.iterator(); iterator.hasNext();) {
					final String query = iterator.next().trim();
					System.out.println("[MSSQLConfigService:] Executing query: "+query);
					try{
					  preStmt=conn.prepareStatement(query);
					  response=preStmt.executeUpdate();
					  System.out.println(response+ " rows affected!");
					}catch (SQLException sqle){
						System.out.println(sqle.getMessage());
					}
				}
			}finally{
				DBConnectionUtil.closeConnection(conn, preStmt);
			}
		}else{
			for (final Iterator<Entry<String, DBConfigModel>> iterator = configMap.entrySet().iterator();
							iterator.hasNext();) {
				final Entry<String, DBConfigModel> eachEntry= iterator.next();
				final String dbName =eachEntry.getKey();
				dbCfgMdl = eachEntry.getValue();
				queries=dbCfgMdl.getStatements().get(dbName);
				System.out.println("[MSSQLConfigService:] Processing request for database name:- "
						+ dbName);
				try{
					conn = DBConnectionUtil.getConnection(ConfigConstants.MSSQLDRIVER,
							url.concat(ConfigConstants.MS_SQL_DBNAME_PREFIX).concat(dbName),userName,password);
					for (final Iterator<String> itr = queries.iterator(); itr.hasNext();) {
						final String query = itr.next().trim();
						System.out.println("[MSSQLConfigService:] Executing query: "+query);
						try{
						  preStmt=conn.prepareStatement(query);
						  response=preStmt.executeUpdate();
						  System.out.println(response+ " rows affected!");
						}catch (SQLException sqle){
							System.out.println(sqle.getMessage());
						}finally{
							DBConnectionUtil.closeConnection(preStmt);
						}
					}
				}finally{
					DBConnectionUtil.closeConnection(conn);
				}
			}
		}
	}
}
