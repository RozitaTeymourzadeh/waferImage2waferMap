/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Filter 
 * Image processing on wafer image 
 * 
 * @author rozitateymourzadeh
 *
 */
public class Filter {
	
	private Service srv = new Service();
	private static Logger LOG = LogManager.getLogger(Filter.class);
	private Integer redIndex = Integer.parseInt(ConfigManager.getConfig().getRedIndex());
	private Integer greenIndex = Integer.parseInt(ConfigManager.getConfig().getGreenIndex());
	
	/*
	 * Constructor
	 * 
	 **/
	public Filter () {
		
	}
	/**
	 * Crop and Save filter
	 * @param image
	 * @param path
	 * @param height
	 * @param width
	 * @param startLine
	 * @param endLine
	 * @param leftLine
	 * @param rightLine
	 * @return image
	 */
	public BufferedImage cropFilter(BufferedImage image, String path, int height, int width, int startLine,int endLine, int leftLine, int rightLine) {
		LOG.info("CropFilter process is started!");
		BufferedImage imgOriginal = image;
		image = srv.map(width, height);
		for (int w = leftLine; w <= rightLine; w++)
		{
			for (int h = startLine; h < endLine; h++)
			{
				image.setRGB(w - leftLine, h - startLine, imgOriginal.getRGB(w, h));
			}
		}
		srv.savePNG(image, path + "_Crop.png");
		return image;
	}
	
	/**
	 * makeRGB
	 * Generate RGB from r, g, b index
	 *
	 *@param rgb
	 *@return RGB
	 */
	public int makeRGB(int rgb) {
		int result = 0;
		float red = (rgb >> redIndex) & 0xFF;
		float green = (rgb >> greenIndex) & 0xFF;
		float blue = (rgb & 0xFF);
		//convert the pixel to black and white
		result = (int)(red*0.299 + green*0.587 + blue*0.114);
		return result;
	}
}
