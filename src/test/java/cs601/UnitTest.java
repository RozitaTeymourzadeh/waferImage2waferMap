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

/**
 * @author rozitateymourzadeh
 *
 */
public class UnitTest {

	private static ConfigManager CONFIG = null;

	
	@Test
	public void testConfig() throws IOException {
		CONFIG = ConfigManager.getConfig();
		Assert.assertNotNull(CONFIG);
	}
	
	@Test
	public void testCache() throws IOException {
		File folder = new File(ConfigManager.getConfig().getInput());
		File cacheFolder = new File(folder.getParent(), "cache");
		Assert.assertNotNull(cacheFolder);
	}
	
	@Test
	public void testInput() throws IOException {
		String inputFile = ConfigManager.getConfig().getInput();
		Assert.assertEquals("waferIMG", inputFile);
	}
	
	@Test
	public void testOutput() throws IOException {
		String outputFile = ConfigManager.getConfig().getOutput();
		Assert.assertEquals("waferTXT", outputFile);
	}
	
	@Test
	public void testGrayScale() throws IOException {
		String grayScale = ConfigManager.getConfig().getGrayScale();
		Assert.assertEquals(256, Integer.parseInt(grayScale));
	}
	
	@Test
	public void testRedIndex() throws IOException {
		String redIndex = ConfigManager.getConfig().getRedIndex();
		Assert.assertEquals(16, Integer.parseInt(redIndex));
	}
	
	@Test
	public void testGreenIndex() throws IOException {
		String greenIndex = ConfigManager.getConfig().getGreenIndex();
		Assert.assertEquals(8, Integer.parseInt(greenIndex));
	}
	
	@Test
	public void testImage() throws IOException {
		File folder = new File(ConfigManager.getConfig().getInput());
		File[] imgs = folder.listFiles();
		File imageFile = imgs[0];
		BufferedImage image = ImageIO.read(imageFile);
		Assert.assertNotNull(image);
	}
	
	
	
}
