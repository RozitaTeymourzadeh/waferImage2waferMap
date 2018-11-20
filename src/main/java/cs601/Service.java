/**
 * 
 */
package cs601;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rozitateymourzadeh
 *
 */
public class Service {
	
	private static Logger LOG = LogManager.getLogger(Service.class);

	/**
	 * create Cache folder
	 * @param cacheFolder 
	 */
	public void createCache(File cacheFolder) {
		try {
			cacheFolder.mkdir();
			LOG.info("Create cache folder:" + cacheFolder.getAbsolutePath());
		} catch(Exception e) {
			LOG.error("FATAL: Exception occured while generating cache file in: " + Run.class.getName());
		}
	}

}
