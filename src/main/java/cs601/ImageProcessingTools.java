/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * Image Processing Tools
 * A class tool to convert image to Black and white image
 * @author Rozita Teymourzadeh
 *
 */
public class ImageProcessingTools {
	
	private int[] statR = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
	private int[] statG = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
	private int[] statB = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
	private int[] state = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
	private int hStep = Integer.parseInt(ConfigManager.getConfig().getHStep());
	private int wStep = Integer.parseInt(ConfigManager.getConfig().getWStep());
	private Integer redIndex = Integer.parseInt(ConfigManager.getConfig().getRedIndex());
	private Integer greenIndex = Integer.parseInt(ConfigManager.getConfig().getGreenIndex());
	private Integer blackWhiteThr = Integer.parseInt(ConfigManager.getConfig().getBlackWhiteThr());
	private int rgb;
	Filter filter = new Filter();
	
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
		
		for (int h = 1; h < height; h += hStep)
		{
			for (int w = 1; w < width; w += wStep)
			{
				rgb = image.getRGB(w, h);
				int red = (rgb >> redIndex) & 0xFF;
				int green = (rgb >> greenIndex) & 0xFF;
				int blue = (rgb & 0xFF);

				statR[red]++;
				statG[green]++;
				statB[blue]++;

				state[filter.makeRGB(rgb)]++;
			}
		}
		return state;
	}
	
	/**
	 * Normalize RGB to Black and White 
	 * 
	 * @param image
	 * @param height
	 * @param width
	 * 
	 * @return image
	 */
	public BufferedImage normalizedBlackWhite(BufferedImage image, int height,int width) {

		for (int h = 1; h < height; h+=hStep)
		{
			for (int w = 1; w<width; w+=wStep)
			{
				rgb = image.getRGB(w, h);
				int blue = (rgb & 0xFF);
				rgb = filter.makeRGB(rgb);

				for(int x = 0; x < wStep && w+x < width; x++){
					for(int y = 0; y < hStep && h+y < height; y++){
						int value = image.getRGB(w+x, h+y);
						rgb += filter.makeRGB(value);
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
