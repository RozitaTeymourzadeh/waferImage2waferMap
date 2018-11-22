/**
 * 
 */
package cs601;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author rozitateymourzadeh
 *
 */
public class ConfigManager {

	private static Logger LOG = LogManager.getLogger(ConfigManager.class);
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
				LOG.error("The configuration file is not find.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
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
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}	
	
	/**
	 * 
	 * Top Diode Threshold
	 * Pattern similarity percentage for upper part of image
	 * 0.3f
	 * 
	 */
	public String getThrUp() {
		String result = "";
		if(prop.get("thrUp") != null) {
			result = prop.get("thrUp").toString();
		}else {
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}	
	
	/**
	 * 
	 * Down Diode Threshold
	 * Pattern similarity percentage for down part of image
	 * 0.3f
	 * 
	 */
	public String getThrDown() {
		String result = "";
		if(prop.get("thrDown") != null) {
			result = prop.get("thrDown").toString();
		}else {
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}	
	
	/**
	 * 
	 * Left Diode Threshold
	 * Pattern similarity percentage for left part of image
	 * 0.3f
	 * 
	 */
	public String getThrLeft() {
		String result = "";
		if(prop.get("thrLeft") != null) {
			result = prop.get("thrLeft").toString();
		}else {
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}
	
	/**
	 * 
	 * Right Diode Threshold
	 * Pattern similarity percentage for right part of image
	 * 0.3f
	 * 
	 */
	public String getThrRight() {
		String result = "";
		if(prop.get("thrRight") != null) {
			result = prop.get("thrRight").toString();
		}else {
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}
	
	/**
	 * 
	 * Right Diode Threshold
	 * Pattern similarity percentage for right part of image
	 * 0.3f
	 * 
	 */
	public String getMaxDiodePerLine() {
		String result = "";
		if(prop.get("maxDiodePerLine") != null) {
			result = prop.get("maxDiodePerLine").toString();
		}else {
			LOG.error("Property is Not find. Check the configuration file.");
		}
		return result;
	}

}
