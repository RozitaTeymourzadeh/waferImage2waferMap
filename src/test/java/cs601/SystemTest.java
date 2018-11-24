/**
 * 
 */
package cs601;

import java.io.File;
import java.io.IOException;
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
 * @author Rozita Teymourzadeh
 *
 */
public class SystemTest {

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

	@Test
	public void testNoImage() throws IOException {

		File folder = new File("");
		Conversion conv = new Conversion();
		int result = 0;
		File[] imgs = folder.listFiles();
		if (imgs != null) {
			for(int i = 0; i < imgs.length; i++){
				if(imgs[i].getName().toLowerCase().endsWith(".jpg") || imgs[i].getName().toLowerCase().endsWith(".bmp")|| imgs[i].getName().toLowerCase().endsWith(".png")){
					conv.convert(imgs[i]);
				}else {
					result = -1;
				}
			}
		}else {
			result = -1;
		}
		Assert.assertEquals(-1, result);
	}

}