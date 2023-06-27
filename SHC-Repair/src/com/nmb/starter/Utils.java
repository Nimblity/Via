/**
 * 
 */
package com.nmb.starter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author kisho
 *
 */
public class Utils {
	
	public static Properties getPropertyConfig(String propertyfileName) {
		Properties prop = new Properties();
		System.out.println(propertyfileName);
		String configfilepath = "./resources/config.properties";
		FileInputStream input = null;
		try {
			input = new FileInputStream(configfilepath);
			prop.load(input);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
		return prop;
	}
	
	public static void Unzipper() {
		String filepath = "C:\\VMDEV\\VMINSTALL\\2023-06-23_SystemHealthCheckRepair_AK\\httpd-2.4.57-win64-VS17.zip";
		
	}
	
}
