/**
 * 
 */
package com.nmb.starter;

import java.io.File;
import java.util.Properties;
import java.util.logging.Logger;

import com.nmb.repair.RepairApache;

/**
 * @author kisho
 *
 */

public class Launcher {
	public static void main(String[] args) {
		Properties prop = Utils.getPropertyConfig("config.properties");

		Logger logger = Utils.getLogger();
		String newPackageLocation = prop.getProperty(RepairConstants.NEW_PACKAGE_LOCATION);
		logger.info("===========Starting repair===========");
		Utils.createBackupFolder();

		try {
			File packageObj = new File(newPackageLocation);
			File[] packages = packageObj.listFiles();
			for (File software : packages) {
				String softwareName = software.getName();

				switch (softwareName) {
				case "httpd-2.4.57-win64-VS17.zip ": {
					logger.info("Apache Repair - Starts");
					RepairApache.repairApache(software);
					logger.info("Apache Repair - Ends");
					break;
				}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
