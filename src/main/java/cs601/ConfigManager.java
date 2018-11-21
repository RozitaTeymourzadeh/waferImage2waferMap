/**
 * 
 */
package cs601;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author rozitateymourzadeh
 *
 */
public class ConfigManager {

	private static Properties prop = new Properties();
	private static ConfigManager CONFIG = null;
	private static String PROJECT_CONFIG_FILE = "src/main/resources/config.file";

	/**
	 * constructor
	 * 
	 */
	private ConfigManager(){

	}

	/**
	 * To return configuration object
	 * 
	 * @param configuration file as argument
	 * @return Configuration object
	 */
	public static ConfigManager getConfig() {
		if(null == CONFIG) {
			InputStream input;
			try {
				input  = new FileInputStream(PROJECT_CONFIG_FILE);
				prop.load(input);
				CONFIG = new ConfigManager();
			} catch (IOException e) {
				System.out.println("The configuration file is not find.");
			} 	
		}
		return CONFIG;
	}

	/**
	 * 
	 * Input file
	 * 
	 */
	public String getInput() {
		String result = "";
		if(prop.get("input") != null) {
			result = prop.get("input").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}

	/**
	 * 
	 * Output file
	 * 
	 */
	public String getOutput() {
		String result = "";
		if(prop.get("output") != null) {
			result = prop.get("output").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	

	/**
	 * 
	 * grayScale
	 * 
	 */
	public String getGrayScale() {
		String result = "";
		if(prop.get("grayScale") != null) {
			result = prop.get("grayScale").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	

	/**
	 * 
	 * redIndex
	 * 
	 */
	public String getRedIndex() {
		String result = "";
		if(prop.get("redIndex") != null) {
			result = prop.get("redIndex").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}

	/**
	 * 
	 * greenIndex
	 * 
	 */
	public String getGreenIndex() {
		String result = "";
		if(prop.get("greenIndex") != null) {
			result = prop.get("greenIndex").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}

	/**
	 * 
	 * Black and White Threshold
	 * 
	 */
	public String getBlackWhiteThr() {
		String result = "";
		if(prop.get("blackWhiteThr") != null) {
			result = prop.get("blackWhiteThr").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}


	/**
	 * 
	 * Die Size Threshold
	 * 1.5f
	 * 
	 */
	public String getDieSizeThr() {
		String result = "";
		if(prop.get("dieSizeThr") != null) {
			result = prop.get("dieSizeThr").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	

	/**
	 * 
	 * dieDistanceTolerance
	 * Distance between 2 dies
	 * 5
	 * 
	 */
	public String getDieDistanceTolerance() {
		String result = "";
		if(prop.get("dieDistanceTolerance") != null) {
			result = prop.get("dieDistanceTolerance").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	
	
	/**
	 * 
	 * hStep
	 * height step walking through image
	 * 1
	 * 
	 */
	public String getHStep() {
		String result = "";
		if(prop.get("hStep") != null) {
			result = prop.get("hStep").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	
	
	/**
	 * 
	 * wStep
	 * width step walking through image
	 * 1
	 * 
	 */
	public String getWStep() {
		String result = "";
		if(prop.get("wStep") != null) {
			result = prop.get("wStep").toString();
		}else {
			System.out.println("Property is Not find. Check the configuration file.");
		}
		return result;
	}	

}
