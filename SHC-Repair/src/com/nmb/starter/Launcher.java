/**
 * 
 */
package com.nmb.starter;

import java.util.Properties;

/**
 * @author kisho
 *
 */
public class Launcher {
	public static void main(String[] args) {
		System.out.println("This is the Launcher - Main class");
		
		//Reading the config.property file
		Properties prop = Utils.getPropertyConfig("config.properties");
		System.out.println(prop.getProperty("username"));
		System.out.println(prop.getProperty("password"));
		
	}

}
