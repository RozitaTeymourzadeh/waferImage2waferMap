/**
 * 
 */
package cs601;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rozitateymourzadeh
 *
 */
public class Service {
	
	private static Logger LOG = LogManager.getLogger(Service.class);

	/**
	 * create Cache folder
	 * @param cacheFolder 
	 */
	public void createCache(File cacheFolder) {
		try {
			cacheFolder.mkdir();
			LOG.info("Create cache folder:" + cacheFolder.getAbsolutePath());
		} catch(Exception e) {
			LOG.error("FATAL: Exception occured while generating cache file in: " + Run.class.getName());
		}
	}
	
	/**
	 * Save PNG image
	 * @param BufferdImage
	 * @param String 
	 */
	public void savePNG(final BufferedImage bi, final String path){
		try {
			RenderedImage rendImage = bi;
			ImageIO.write(rendImage, "png", new File(path));
		} catch ( IOException e) {
			LOG.error("FATAL: failed to save PNG file.");
		}
	}
	
	/**
	 * Buffer the image and return buffered Image
	 * @param int X
	 * @param int Y
	 *  
	 * @return Buffered Image
	 */	
	public BufferedImage map(int sizeX, int sizeY){
		final BufferedImage res = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
		for (int x = 0; x < sizeX; x++){
			for (int y = 0; y < sizeY; y++){
				res.setRGB(x, y, Color.WHITE.getRGB() );
			}
		}
		return res;
	}

}
