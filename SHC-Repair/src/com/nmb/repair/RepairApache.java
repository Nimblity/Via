/**
 * 
 */
package com.nmb.repair;

import java.io.File;
import java.util.Properties;
import java.util.regex.Pattern;

import com.nmb.starter.Utils;

/**
 * @author kisho
 *
 */
public class RepairApache {
	
	public static void repairApache() {
		Properties prop = Utils.getPropertyConfig("config.properties");
		String newPackageLocation = prop.getProperty("newPackageLocation");
		
		try {
			//check if apache related zips are present or not:
			File fObj = new File(newPackageLocation);
			File[] listoffile = fObj.listFiles();
			for (File file : listoffile) {
				if (file.getName().contains("httpd")) {
					System.out.println("Matchfound");
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

}
