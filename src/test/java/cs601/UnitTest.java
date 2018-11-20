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
	public void testBlackWhiteThr() throws IOException {
		String blackWhiteThr = ConfigManager.getConfig().getBlackWhiteThr();
		Assert.assertEquals(40, Integer.parseInt(blackWhiteThr));
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
	public void testDieSizeThr() throws IOException {
		String dieSizeThr = ConfigManager.getConfig().getDieSizeThr();
		Assert.assertEquals(1.5f, Float.parseFloat(dieSizeThr),0.1f);
	}
	
	@Test
	public void testDieDistanceTolerance() throws IOException {
		String dieDistanceTolerance = ConfigManager.getConfig().getDieDistanceTolerance();
		Assert.assertEquals(5, Integer.parseInt(dieDistanceTolerance));
	}
	
}
