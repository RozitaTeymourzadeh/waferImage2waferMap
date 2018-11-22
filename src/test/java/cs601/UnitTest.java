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
	
	@Test
	public void testHStep() throws IOException {
		String hStep = ConfigManager.getConfig().getHStep();
		Assert.assertEquals(1, Integer.parseInt(hStep));
	}
	
	@Test
	public void testWStep() throws IOException {
		String wStep = ConfigManager.getConfig().getWStep();
		Assert.assertEquals(1, Integer.parseInt(wStep));
	}
	
	@Test
	public void testThrUp() throws IOException {
		String thrUp = ConfigManager.getConfig().getThrUp();
		Assert.assertEquals(0.3f, Float.parseFloat(thrUp),0.0f);
	}
	
	@Test
	public void testThrDown() throws IOException {
		String thrDown = ConfigManager.getConfig().getThrDown();
		Assert.assertEquals(0.3f, Float.parseFloat(thrDown),0.0f);
	}
	
	@Test
	public void testThrLeft() throws IOException {
		String thrLeft = ConfigManager.getConfig().getThrLeft();
		Assert.assertEquals(0.3f, Float.parseFloat(thrLeft),0.0f);
	}
	
	@Test
	public void testThrRight() throws IOException {
		String thrRight = ConfigManager.getConfig().getThrRight();
		Assert.assertEquals(0.3f, Float.parseFloat(thrRight),0.0f);
	}
	
	@Test
	public void testMaxDiodePerLine() throws IOException {
		String maxDiodePerLine = ConfigManager.getConfig().getMaxDiodePerLine();
		Assert.assertEquals(300, Integer.parseInt(maxDiodePerLine));
	}
	
}
