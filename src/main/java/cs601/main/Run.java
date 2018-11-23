/**
 * 
 */
package cs601.main;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.Filter.Conversion;
import cs601.Service.ConfigManager;
import cs601.Service.Service;

/**
 * Wafer2Map application converts wafer image to wafermap
 * WaferMap is readable by the CNC machinery to place the chip processor on the PCB board.
 * 
 * @author rozitateymourzadeh
 */
public class Run {
	private static Logger LOG = LogManager.getLogger(Run.class);
	private static File cacheFolder = null;
	public static void main(String[] args) {

		LOG.info("Convesion is started ..." + Run.class.getName());
		File folder = new File(ConfigManager.getConfig().getInput());
		cacheFolder = new File(folder.getParent(), "cache");
		Conversion conv = new Conversion();
		Service srv = new Service();
		srv.createCache(cacheFolder);
		File[] imgs = folder.listFiles();
		for(int i = 0; i < imgs.length; i++){
			if(imgs[i].getName().toLowerCase().endsWith(".jpg") || imgs[i].getName().toLowerCase().endsWith(".bmp")|| imgs[i].getName().toLowerCase().endsWith(".png")){
				conv.convert(imgs[i]);
			}
		}
		srv.delete(folder);
		LOG.info("Conversion Process was completed!!");
	}
}
