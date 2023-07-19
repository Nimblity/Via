/**
 * 
 */
package com.nmb.starter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author kisho
 *
 */
public class Utils {

	static Properties prop = new Properties();
	
	public static void copyDirectory(String sourceDirectory, String targetDirectory) {
		try {
			Path sourceDirectoryPath = Paths.get(sourceDirectory);
			Path targetDirectoryPath = Paths.get(targetDirectory);

			Files.walk(sourceDirectoryPath).forEach(src -> {
				try {
					Files.copy(src, targetDirectoryPath.resolve(sourceDirectoryPath.relativize(src)),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static boolean deleteDirectory(File fileToBeDeleted) {
		boolean status = false;
		File[] lstFile = fileToBeDeleted.listFiles();
		
		for (File file : lstFile) {
			if(file.isDirectory()) {
				deleteDirectory(file);
			}
			file.delete();
			
		}
		status = true;
		return status;
	}

	public static boolean moveApacheDirectory(String sourceFileLocation, String targetFileLocation) {
		
		boolean status = false;
		targetFileLocation = targetFileLocation + RepairConstants.BACKSLASH_TWO + prop.getProperty(RepairConstants.APACHE_DIR_NAME); ;
		copyDirectory(sourceFileLocation, targetFileLocation);
		File sourceFile = new File(sourceFileLocation);
		deleteDirectory(sourceFile);
		status = true;
		return status;
		
	}

	public static Properties getPropertyConfig(String propertyfileName) {
		String configfilepath = "./resources/config.properties";
		FileInputStream input = null;
		try {
			input = new FileInputStream(configfilepath);
			prop.load(input);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static void unzipFile(String zipFilePath, String outputFolderPath) {
		String extractedFileName = null;
		ZipEntry zipEntry = null;

		try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)))) {
			byte[] buffer = new byte[1024];
			zipEntry = zipInputStream.getNextEntry();

			while (zipEntry != null) {
				extractedFileName = zipEntry.getName();
				File extractedFile = new File(outputFolderPath + File.separator + extractedFileName);
				if (!zipEntry.isDirectory()) {
					// Create directories for any nested folders
					new File(extractedFile.getParent()).mkdirs();

					// Extract the file
					FileOutputStream outputStream = new FileOutputStream(extractedFile);
					int length;
					while ((length = zipInputStream.read(buffer)) > 0) {
						outputStream.write(buffer, 0, length);
					}
					outputStream.close();
				}

				zipEntry = zipInputStream.getNextEntry();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Creates the instance of Logger
	public static Logger getLogger() {
		Logger logger = Logger.getLogger("MyLog");
		try {
			FileHandler filehandler = new FileHandler(prop.getProperty(RepairConstants.LOG_FILE_LOCATION));
			logger.addHandler(filehandler);
			filehandler.setFormatter(new SimpleFormatter());

		} catch (Exception e) {
			logger.log(Level.WARNING, "Exception : " + e);
			e.printStackTrace();
		}
		return logger;
	}

	// Creates the backup folder
	public static void createBackupFolder() {
		String backupFolder = RepairConstants.BACKUP;
		String backupFolderPath = prop.getProperty("newPackageLocation").concat("/").concat(backupFolder);

		File dir_backupFolder = new File(backupFolderPath);

		if (!dir_backupFolder.exists()) {
			dir_backupFolder.mkdir();

		}
	}

}
