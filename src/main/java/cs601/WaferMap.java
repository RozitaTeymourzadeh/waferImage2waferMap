/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * @author rozitateymourzadeh
 *
 */
public class WaferMap {
	
	PatternDetector patternDetector = new PatternDetector();
	float thrUp = Float.parseFloat(ConfigManager.getConfig().getThrUp());
	float thrDown = Float.parseFloat(ConfigManager.getConfig().getThrDown());
	
	
	/**
	 * @param image
	 * @param height
	 * @param width
	 * @param pattern
	 * @param thrUp
	 * @param thrDown
	 * @param patternDetector
	 * @param waferMap
	 * @return 
	 */

	public boolean[][] createWaferMap(BufferedImage image, int height, int width, BufferedImage pattern ) {
				
		boolean[][] waferMap = new boolean[300][300];
		for(int i = 0; i < 300; i++){
			for(int j = 0; j < 300; j++){
				waferMap[i][j] = false;
			}	
		}

		int x = width / 2;
		int y = height / 2;

		float[][] sim = new float[pattern.getWidth()][pattern.getHeight()];

		for(int i = 0; i < pattern.getWidth(); i++){
			for(int j = 0; j < pattern.getHeight(); j++){
				sim[i][j] = patternDetector.getSimilarity(image, x+i, y+j, pattern);
			}
		}
		int x0 = 0;
		int y0 = 0;
		for(int i = 0; i < pattern.getWidth(); i++){
			for(int j = 0; j < pattern.getHeight(); j++){
				if(sim[i][j] > sim[x0][y0]){
					x0 = i;
					y0 = j;
				}
			}
		}
		x0 += x;
		y0 += y;

		x = x0;
		y = y0;

		int index = 150;
		for( ; index > 0 && y > 1; y -= pattern.getWidth(), index--){
			sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = patternDetector.getSimilarity(image, x+i, y+j, pattern);
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


			if(sim[xTemp][yTemp] > thrUp){
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
			}

			boolean[] left = patternDetector.findLeft(image, pattern, x, y);
			boolean[] right = patternDetector.findRight(image, pattern, x, y);
			for(int i = 100 - 1, j = 150; i >= 0; i--, j--)
			{
				waferMap[index][j] = left[i];
			}
			for(int i = 0, j = 150; i < 100; i++, j++)
			{
				waferMap[index][j] = right[i];
			}
		}

		x = x0;
		y = y0;

		index = 150;
		for( ; index < 300 && y < image.getHeight() - pattern.getHeight() - 1; y += pattern.getWidth(), index++){
			sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = patternDetector.getSimilarity(image, x+i, y+j, pattern);
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

			if(sim[xTemp][yTemp] > thrDown){
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
			}

			boolean[] right = patternDetector.findRight(image, pattern, x, y);
			boolean[] left = patternDetector.findLeft(image, pattern, x, y);
			for(int i = 100 - 1, j = 150; i >= 0; i--, j--)
			{
				waferMap[index][j] = left[i];
			}
			for(int i = 0, j = 150; i < 100; i++, j++)
			{
				waferMap[index][j] = right[i];
			}

		}
		return waferMap;
	}
}
