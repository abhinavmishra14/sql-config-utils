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
package com.abhinav.sqlcfgutil.model;

import java.util.List;
import java.util.Map;

/**
 * The Class DBConfigModel.
 */
public class DBConfigModel {
	
	/** The db name. */
	private String dbName;
	
	/** The statements. */
	private Map<String, List<String>> statements;

	
	/**
	 * Gets the db name.
	 *
	 * @return the db name
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Sets the db name.
	 *
	 * @param dbName the new db name
	 */
	public void setDbName(final String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Gets the statements.
	 *
	 * @return the statements
	 */
	public Map<String, List<String>> getStatements() {
		return statements;
	}

	/**
	 * Sets the statements.
	 *
	 * @param statements the statements
	 */
	public void setStatements(final Map<String, List<String>> statements) {
		this.statements = statements;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[dbName=" + dbName
				+ ", statements=" + statements + "]";
	}
}
