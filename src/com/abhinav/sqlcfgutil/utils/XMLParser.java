/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2014. Abhinav Kumar Mishra. 
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import com.abhinav.sqlcfgutil.model.DBConfigModel;

/**
 * The Class XMLParser.
 */
public class XMLParser extends XMLFilterImpl {

	/** The value. */
	private String value;

	/** The db name. */
	private String dbName;
	
	/** The stmt map. */
	private Map<String, List<String>> stmtMap;

	/** The stmts. */
	private List<String> stmts;

	/** The config map. */
	private Map<String, DBConfigModel> configMap;

	/** The dbcfg. */
	private DBConfigModel dbcfg = null;

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.XMLFilterImpl#characters(char[], int, int)
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String val = String.valueOf(ch).substring(start, start + length).trim();
		if (value == null) {
			value = val;
		} else {
			value += val;
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.XMLFilterImpl#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (qName.equals("dbconfig")) {
			configMap = new LinkedHashMap<String, DBConfigModel>();
		} else if (qName.equals("database")) {
			dbcfg = new DBConfigModel();
			stmtMap = new LinkedHashMap<String, List<String>>();
			dbName = atts.getValue("dbname");
			dbcfg.setDbName(dbName);
		} else if (qName.equals("statements")) {
			stmts = new ArrayList<String>();
		}
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.helpers.XMLFilterImpl#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equals("statement")) {
			stmts.add(value);
		} else if (qName.equals("statements")) {
			stmtMap.put(dbName, stmts);
			dbcfg.setStatements(stmtMap);
		} else if (qName.equals("database")) {
			configMap.put(dbName, dbcfg);
			dbcfg=null;
			dbName=null;
		}
		value = null;
	}

	/**
	 * Gets the config map.
	 *
	 * @return the config map
	 */
	public Map<String, DBConfigModel> getConfigMap() {
		return configMap;
	}

	/**
	 * Sets the config map.
	 *
	 * @param configMap the config map
	 */
	public void setConfigMap(Map<String, DBConfigModel> configMap) {
		this.configMap = configMap;
	}
}
