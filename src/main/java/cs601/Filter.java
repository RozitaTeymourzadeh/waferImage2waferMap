/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * @author rozitateymourzadeh
 *
 */
public class Filter {
	
	/**
	 * Crop filter
	 * To cut the image based on available diode
	 * 
	 * @param image
	 * @param path
	 * @param startLine
	 * @param endLine
	 * @param leftLine
	 * @param rightLine
	 * @param width
	 * @param height
	 */
	public void cropFilter(BufferedImage image, String path, int startLine, int endLine,int leftLine, int rightLine, int width, int height) {
		Service srv = new Service();
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
	}

}
