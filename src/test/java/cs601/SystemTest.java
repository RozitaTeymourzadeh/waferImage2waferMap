/**
 * 
 */
package cs601;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import cs601.Filter.Conversion;
import cs601.Service.ConfigManager;
import cs601.Service.Service;

/**
 * System Test
 * 
 * @author rozitateymourzadeh
 *
 */
public class SystemTest {
	private static Logger LOG = LogManager.getLogger(SystemTest.class);
	
	@Test
	public void testOutput() throws IOException {
		Logger LOG = LogManager.getLogger(SystemTest.class);
			LOG.info("SystemTest is started ..." + SystemTest.class.getName());
			File cacheFolder = null;
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
			LOG.info("Conversion Process was completed!!");
		Assert.assertNotNull("waferTXT");
	}
}