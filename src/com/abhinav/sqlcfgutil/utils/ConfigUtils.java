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

/**
 * The Class ConfigUtils.
 */
public class ConfigUtils {
	
	/**
	 * Convert to linux format.
	 *
	 * @param inputPath the input path
	 * @return the string
	 */
	public static String convertToLinuxFormat(final String inputPath) {
		return inputPath.replace(ConfigConstants.FILE_SEPERATOR_WIN,
				ConfigConstants.FILE_SEPERATOR_LINUX);
	}
}
