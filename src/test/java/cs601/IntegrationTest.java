/**
 * 
 */
package cs601;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;

import cs601.Filter.Filter;
/**
 * Integration Test
 * 
 * @author rozitateymourzadeh
 *
 */
public class IntegrationTest {
	
	@Test
	public void testCropFilter() throws IOException {
		Filter filter = new Filter();
		File folder = new File("cache");
		String path = folder.getAbsolutePath();
		File[] imgs = folder.listFiles();
		BufferedImage image = ImageIO.read(imgs[0]);
		BufferedImage cropped = filter.cropFilter(image, path+"/", 200, 200, 0, 100, 100, 0);
		Assert.assertNotNull(cropped);
	}

}
