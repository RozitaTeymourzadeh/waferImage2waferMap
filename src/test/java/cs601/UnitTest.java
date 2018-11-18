/**
 * 
 */
package cs601;


import java.io.IOException;
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
	public void testInput() throws IOException {
		String inputFile = ConfigManager.getConfig().getInput();
		Assert.assertEquals("waferIMG", inputFile);
	}
	
	@Test
	public void testOutput() throws IOException {
		String outputFile = ConfigManager.getConfig().getOutput();
		Assert.assertEquals("waferTXT", outputFile);
	}
	
	
}
