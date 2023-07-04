/**
 * 
 */
package com.nmb.starter;

import java.util.Properties;

import com.nmb.repair.RepairApache;

/**
 * @author kisho
 *
 */
public class Launcher {
	public static void main(String[] args) {
		System.out.println("This is the Launcher - Main class");
		
		//Starting the RepairApache
		//Checking commit
		RepairApache.repairApache();
	
		
	}

}
