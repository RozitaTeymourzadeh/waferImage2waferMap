/**
 * 
 */
package cs601.Tool;

import java.awt.image.BufferedImage;
import java.io.IOException;
import cs601.Service.ConfigManager;
import cs601.Service.Service;

/**
 * PatternDetector
 * To detect the healthy Die in the image 
 * 
 * @author Rozita Teymourzadeh
 *
 */
public class PatternDetector {
	private float thrLeft = Float.parseFloat(ConfigManager.getConfig().getThrLeft());
	private float thrRight = Float.parseFloat(ConfigManager.getConfig().getThrRight());
	private Integer avgComponent = Integer.parseInt(ConfigManager.getConfig().getAveComponant());
	private Service srv = new Service();
	
	/**
	 * Constructor
	 */
	public PatternDetector() {
		
	}
	
	/**
	 * findLeft method
	 * To find pattern in the left part of the image starting from y,x coordinate 
	 * 
	 * @param x
	 * @param y
	 * @param pattern
	 * @param image
	 * @throws IOException
	 * 
	 * @return boolean[] result
	 */
	public boolean[] findLeft(BufferedImage img, BufferedImage pattern, int x, int y) throws IOException{
		boolean[] result = new boolean[avgComponent];
		int index = avgComponent - 1;
		for( ; index >= 0 && x >= 1; x -= pattern.getWidth(), index--){
			float[][] sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = this.getSimilarity(img, x+i, y+j, pattern);
				}	
			}

			int xTemp = 1;
			int yTemp = 1;

			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					if(sim[i][j] > sim[xTemp][yTemp]){
						xTemp = i;
						yTemp = j;
					}
				}	
			}

			if(sim[xTemp][yTemp] > thrLeft){
				result[index] = true;
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
				result[index] = false;
			}
		}

		for(; index >= 0; index--){
			result[index] = false;
		}

		return result;
	}

	/**
	 * findRight method
	 * To find pattern in the Right part of the image starting from y,x coordinate 
	 * 
	 * @param x
	 * @param y
	 * @param pattern
	 * @param image
	 * @throws IOException
	 */
	public boolean[] findRight(BufferedImage img, BufferedImage pattern, int x, int y) throws IOException{
		boolean[] result = new boolean[avgComponent];
		int index = 0;
		for( ; index < avgComponent && x < img.getWidth() - pattern.getWidth() - 1; x += pattern.getWidth(), index++){
			float[][] sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = this.getSimilarity(img, x+i, y+j, pattern);
				}	
			}

			int xTemp = 1;
			int yTemp = 1;

			for(int i = 0; i < 3; i++){
				for(int j = 0; j < 3; j++){
					if(sim[i][j] > sim[xTemp][yTemp]){
						xTemp = i;
						yTemp = j;
					}
				}
			}

			if(sim[xTemp][yTemp] > thrRight){
				result[index] = true;
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
				result[index] = false;
			}
		}

		for(; index < avgComponent; index++){
			result[index] = false;
		}

		return result;
	}

	/**
	 * getSimilarity method
	 * To find similar die with pattern in the image
	 * 
	 * @param x
	 * @param y
	 * @param pattern
	 * @param image
	 * @throws IOException
	 * 
	 * @return boolean result
	 */
	static int cacheCounter = 0;
	public float getSimilarity(BufferedImage img, int x, int y, BufferedImage pattern){
		float diff = 0;

		int width = pattern.getWidth();
		int height = pattern.getHeight();

//		BufferedImage cache = srv.map(width, height);

		int counter = 0;

		float diameter = (float) Math.sqrt((width*width + height*height)) / 2;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				counter++;
				if((x+i) >= img.getWidth() || (y+j) >= img.getHeight() || img.getRGB(x+i, y+j) != pattern.getRGB(i, j)){
					diff += (float) Math.sqrt((width/2 - i)*(width/2 - i) + (height/2 - j)*(height/2 - j)) / diameter;
				} else{
				}
			}
		}

		float res = 1 - diff / counter;//(width * height);
		return res*res*res*res;
	}

	/**
	 * getPattern method
	 * To find pattern in the image
	 * 
	 * @param size
	 * @throws IOException
	 * 
	 * @return BufferedImage result
	 */
	public BufferedImage getPattern(int[] size) throws IOException{
		BufferedImage result = srv.map(size[0], size[1]);
		for(int i = 0; i < size[0]; i++){
			for(int j = 0; j < size[1]; j++){
				if(i == 0 || i == size[0] - 1 || j == 0 || j == size[1] - 1){
					result.setRGB(i, j, 0xFFFFFF);
				} else {
					result.setRGB(i, j, 0);
				}
			}
		}
		return result;
	}
}
