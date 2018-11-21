/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * @author rozitateymourzadeh
 *
 */
public class ImageProcessingTools {
	
	public ImageProcessingTools(){
		
	}
	
	/**
	 * Calculate  Black/White Threshold
	 * 
	 * @param image
	 * @param height
	 * @param width
	 * @param hStep
	 * @param wStep
	 * @param stat
	 * @return 
	 */
	public int[] createBlackWhite(BufferedImage image, int height, int width, int hStep, int wStep, int[] state) {
		int rgb;
		int[] statR = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statG = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statB = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];

		for (int h = 1; h < height; h += hStep)
		{
			for (int w = 1; w < width; w += wStep)
			{
				rgb = image.getRGB(w, h);
				//mask rgb and separate the index
				Integer redIndex = Integer.parseInt(ConfigManager.getConfig().getRedIndex());
				Integer greenIndex = Integer.parseInt(ConfigManager.getConfig().getGreenIndex());
				int red = (rgb >> redIndex) & 0xFF;
				int green = (rgb >> greenIndex) & 0xFF;
				int blue = (rgb & 0xFF);

				statR[red]++;
				statG[green]++;
				statB[blue]++;

				state[makeRGB(rgb)]++;
			}
		}
		return state;
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
		Integer redIndex = Integer.parseInt(ConfigManager.getConfig().getRedIndex());
		Integer greenIndex = Integer.parseInt(ConfigManager.getConfig().getGreenIndex());
		float red = (rgb >> redIndex) & 0xFF;
		float green = (rgb >> greenIndex) & 0xFF;
		float blue = (rgb & 0xFF);
		//convert the pixel to black and white
		result = (int)(red*0.299 + green*0.587 + blue*0.114);
		return result;
	}

}
