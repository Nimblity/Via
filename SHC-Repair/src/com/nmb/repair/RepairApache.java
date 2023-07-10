/**
 * 
 */
package com.nmb.repair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;
import java.util.logging.Logger;

import com.nmb.starter.RepairConstants;
import com.nmb.starter.Utils;

/**
 * @author kisho
 *
 */
public class RepairApache {
	static Logger logger = Utils.getLogger();
	static Properties prop = Utils.getPropertyConfig(RepairConstants.CONFIG_PROPERTIES);
	static String vmprogramsAddress = prop.getProperty(RepairConstants.VMPROGRAMS_LOCATION);
	static String ApacheDirName = prop.getProperty(RepairConstants.APACHE_DIR_NAME);
	static String unzippedFilepath = prop.getProperty(RepairConstants.UNZIPPED_FILE_LOCATION);
	static String apachConfDirName = prop.getProperty(RepairConstants.APACHE_CONF_DIR_NAME);
	static String extracteDirName = null;

	public static void repairApache(File file) {
		try {
			String filepath = file.getAbsoluteFile().toString();
			Utils.unzipFile(filepath, unzippedFilepath);
			logger.info("Unizpping of Apache - Ends");

			extracteDirName = executeConfDirExchange(file);
			stopApacheService();
			executeApacheExchange(extracteDirName);
			//deployApache(extracteDirName);
			startApacheService();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will start the Apache Service
	 */
	private static void startApacheService() {
		logger.info(">>>>>Apache Windows Service : Started");

	}

	/**
	 * This will check stop the Apache service
	 */
	private static void stopApacheService() {
		logger.info(">>>>>Apache Windows Service : Stoppped");

	}

	/**
	 * 
	 * @param extractedDirName
	 */
	private static void executeApacheExchange(String extractedDirName) {

		logger.info(">>>>>Apache Exchange : Started");
		// 1. Move the old Apache from VMPROGRAMS to BACKUP
		String sourceDirLocation = prop.getProperty(RepairConstants.VMPROGRAMS_LOCATION) + RepairConstants.BACKSLASH_TWO
				+ ApacheDirName;
		String destDirLocaiton = prop.getProperty(RepairConstants.NEW_PACKAGE_LOCATION) + RepairConstants.BACKSLASH_TWO
				+ RepairConstants.BACKUP;
		Utils.moveDirectory(sourceDirLocation, destDirLocaiton);
		
		logger.info(">>>>>Apache Exchange : Ended");

	}

	/**
	 * Will take the the New Apache and then deploy it in VMPROGRAMS
	 */
	private static void deployApache(String extractedDirName) {
		logger.info(">>>>> Apache Deployment : STARTS");
		String sourceDirLocation = prop.getProperty(RepairConstants.NEW_PACKAGE_LOCATION) + RepairConstants.BACKSLASH_TWO
				+ extractedDirName + RepairConstants.BACKSLASH_TWO + prop.getProperty(RepairConstants.APACHE_DIR_NAME);
		
		String destDirLocation = prop.getProperty(RepairConstants.VMPROGRAMS_LOCATION) + RepairConstants.BACKSLASH_TWO + prop.getProperty(RepairConstants.APACHE_DIR_NAME);
		Utils.copyDirectory(sourceDirLocation, destDirLocation);
		
		logger.info(">>>>> Apache Deployment : ENDS");
	}

	private static String executeConfDirExchange(File file) {
		File fobj = new File(file.getAbsoluteFile().toString());
		String extractedDirName = fobj.getName().substring(0, fobj.getName().lastIndexOf(RepairConstants.DOT));
		logger.info("Exchange of Config - Starts");
		String ApacheDirLocation = vmprogramsAddress + RepairConstants.BACKSLASH_TWO + ApacheDirName;
		File fObj = new File(ApacheDirLocation);
		File[] listofFiles = fObj.listFiles();
		for (File lsFile : listofFiles) {
			if (lsFile.getName().contentEquals(apachConfDirName)) {
				Utils.copyDirectory(ApacheDirLocation + RepairConstants.BACKSLASH_TWO + apachConfDirName,
						unzippedFilepath + RepairConstants.BACKSLASH_TWO + extractedDirName
								+ RepairConstants.BACKSLASH_TWO + ApacheDirName + RepairConstants.BACKSLASH_TWO
								+ apachConfDirName);
				break;
			}

		}
		logger.info(">>>>>>Exchange of config - Ends");
		return extractedDirName;

	}

	

}
