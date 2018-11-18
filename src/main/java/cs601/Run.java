/**
 * 
 */
package cs601;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rozitateymourzadeh
 *
 */
public class Run {
	private static Logger LOG = LogManager.getLogger(Run.class);
	private static File cacheFolder = null;
	
	public static void main(String[] args) {
		
		LOG.info("Convesion is started ..." + Run.class.getName());
		ConfigManager.getConfig().getInput();
		
		File folder = new File(ConfigManager.getConfig().getInput());//Read the waferIMG file
		cacheFolder = new File(folder.getParent(), "cache");
		// create cache file
		try {
			cacheFolder.mkdir();
			LOG.info("Create cache folder:" + cacheFolder.getAbsolutePath());
		} catch(Exception e) {
			LOG.error("FATAL: Exception occured while generating cache file in: " + Run.class.getName());
		}
		
		// read file
		File[] imgs = folder.listFiles();
		for(int i = 0; i < imgs.length; i++){
			if(imgs[i].getName().toLowerCase().endsWith(".jpg") || imgs[i].getName().toLowerCase().endsWith(".bmp")|| imgs[i].getName().toLowerCase().endsWith(".png")){
				convert(imgs[i]);
			}
		}
		
		// Delet processed files
		File[] imgsProcessed  = folder.listFiles();
		for(int i1 = 0 ; i1 < imgsProcessed.length ; i1++) {
			if(imgsProcessed[i1].getName().toLowerCase().contains("crop") || imgsProcessed[i1].getName().toLowerCase().contains("patterns")) {
				imgsProcessed[i1].delete();
			}
		}
		LOG.info("Conversion Process was completed!!");
	}
	
	
	

	private static void convert(File file) {
		System.out.println(file.length());
		
	}
}
