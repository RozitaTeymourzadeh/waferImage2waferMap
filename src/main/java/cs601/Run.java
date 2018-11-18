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
public class Run {
	private static Logger LOG = LogManager.getLogger(Run.class);
	private static File cacheFolder = null;
	
	public static void main(String[] args) {
		
		LOG.info("Convesion is started ..." + Run.class.getName());
		ConfigManager.getConfig().getInput();
		
		File folder = new File(".\\" + ConfigManager.getConfig().getInput());
		cacheFolder = new File(folder.getParent(), "cache");

		File[] imgs = folder.listFiles();
		for(int i = 0; i < imgs.length; i++){
			if(imgs[i].getName().toLowerCase().endsWith(".jpg") || imgs[i].getName().toLowerCase().endsWith(".bmp")|| imgs[i].getName().toLowerCase().endsWith(".png")){
				convert(imgs[i]);
			}
		}
	}

	private static void convert(File file) {
		// TODO Auto-generated method stub
		
	}
}
