/**
 * 
 */
package cs601.Service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.main.Run;

/**
 * @author rozitateymourzadeh
 *
 */
public class Service {
	
	private static Logger LOG = LogManager.getLogger(Service.class);

	/**
	 * Constructor
	 *
	 */
	public Service() {
		
	}
	
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
	 * To save PNG image 
	 * @param BufferedImage
	 * @param Path 
	 */
	public void savePNG(final BufferedImage bufferedImage, final String path){
		try {
			RenderedImage rendImage = bufferedImage;
			ImageIO.write(rendImage, "png", new File(path));
		} catch ( IOException e) {
			LOG.error("FATAL: Failed to save .png image");
		}
	}
	
	/**
	 * To Map the input image in color scale 
	 * @param sizeX
	 * @param sizeY
	 * 
	 * @return BufferedImage
	 *  
	 */
	public BufferedImage map(int sizeX, int sizeY){
		final BufferedImage resultImage = new BufferedImage( sizeX, sizeY, BufferedImage.TYPE_INT_RGB );
		for (int x = 0; x < sizeX; x++){
			for (int y = 0; y < sizeY; y++){
				resultImage.setRGB(x, y, Color.WHITE.getRGB() );
			}
		}
		return resultImage;
	}
	
	/**
	 * Delete Folder
	 * @param folder
	 */
	public void delete(File folder) {
		File[] imgsProcessed  = folder.listFiles();
		for(int i1 = 0 ; i1 < imgsProcessed.length ; i1++) {
			if(imgsProcessed[i1].getName().toLowerCase().contains("crop") || imgsProcessed[i1].getName().toLowerCase().contains("pattern")) {				
				imgsProcessed[i1].delete();
			}
		}
	}
}
