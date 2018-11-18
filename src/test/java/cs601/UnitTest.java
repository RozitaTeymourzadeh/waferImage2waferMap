/**
 * 
 */
package cs601;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @author rozitateymourzadeh
 *
 */
public class UnitTest {

	private static Properties prop = new Properties();
	private static String PROJECT_CONFIG_FILE = "src/main/resources/config.json";
	
	@Test
	public void testConfig() throws IOException {
		JsonParser parser = new JsonParser();
		InputStream input  = new FileInputStream(PROJECT_CONFIG_FILE);
		prop.load(input);
		Reader reader = new InputStreamReader(input);
		JsonElement rootElement = parser.parse(reader);
		Assert.assertNotNull(rootElement);
	}
	
}
