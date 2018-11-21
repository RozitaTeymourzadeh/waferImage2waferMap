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
	 * @return state
	 */
	public int[] createBlackWhite(BufferedImage image, int height, int width) {
		int rgb;
		int[] statR = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statG = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statB = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] state = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int hStep = Integer.parseInt(ConfigManager.getConfig().getHStep());
		int wStep = Integer.parseInt(ConfigManager.getConfig().getWStep());

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
	
	/**
	 * Normalize RGB to Black and White 
	 * 
	 * @param image
	 * @param imageTool
	 * @param height
	 * @param width
	 */
	public BufferedImage normalizedBlackWhite(BufferedImage image, int height,int width) {
		// blue as blue component to detect b/w cropped image
		Integer blackWhiteThr = Integer.parseInt(ConfigManager.getConfig().getBlackWhiteThr());
		int hStep = Integer.parseInt(ConfigManager.getConfig().getHStep());
		int wStep = Integer.parseInt(ConfigManager.getConfig().getWStep());
		ImageProcessingTools imageTool = new ImageProcessingTools();
		int rgb;

		for (int h = 1; h < height; h+=hStep)
		{
			for (int w = 1; w<width; w+=wStep)
			{
				rgb = image.getRGB(w, h);
				int blue = (rgb & 0xFF);
				rgb = imageTool.makeRGB(rgb);

				for(int x = 0; x < wStep && w+x < width; x++){
					for(int y = 0; y < hStep && h+y < height; y++){
						int value = image.getRGB(w+x, h+y);
						rgb += imageTool.makeRGB(value);
					}
				}
				rgb /= (wStep * wStep);

				if(blue < blackWhiteThr){
					rgb = 0;
				} else {
					rgb = 0xFFFFFF;
				}
				image.setRGB(w / wStep, h / hStep, rgb);
			}
		}
		return image;
	}

}
