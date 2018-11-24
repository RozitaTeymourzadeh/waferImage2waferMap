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
import cs601.Service.ConfigManager;
import cs601.Tool.Measurement;
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

	
	@Test
	public void testImage() throws IOException {
		File folder = new File(ConfigManager.getConfig().getInput());
		File[] imgs = folder.listFiles();
		File imageFile = imgs[1];
		BufferedImage image = ImageIO.read(imageFile);
		Assert.assertNotNull(image);
	}
	
	@Test
	public void testOutput() throws IOException {
		String outputFile = ConfigManager.getConfig().getOutput();
		Assert.assertEquals("waferTXT", outputFile);
	}
	
	@Test
	public void testDieSizing() throws IOException {
		Measurement mse = new Measurement();
		File folder = new File("cache");
		File[] imgs = folder.listFiles();
		BufferedImage image = ImageIO.read(imgs[1]);
		int[] result = mse.calcSize(image, 0, 19, 0, 19);
		Assert.assertEquals(0, result[0]);
	}
	
}
