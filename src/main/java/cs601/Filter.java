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
	
	Service srv = new Service();
	
	/**
	 * @param image
	 * @param prefix
	 * @param height
	 * @param width
	 * @param startLine
	 * @param endLine
	 * @param leftLine
	 * @param rightLine
	 * @return
	 */
	public BufferedImage cropFilter(BufferedImage image, String path, int height, int width, int startLine,int endLine, int leftLine, int rightLine) {
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


}
