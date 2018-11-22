/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * @author rozitateymourzadeh
 *
 */
public class Measurement {
	
	ImageProcessingTools imageTool = new ImageProcessingTools();

	public int[] calcSize(BufferedImage img, int left, int right, int top, int bottom){
		
		int dieHeight = 0;
		int spaceHeight = 0;
		int dieWidth = 0;
		int spaceWidth = 0;
		int[] die = new int[100];
		int[] space = new int[100];

		int counterDie = 0;
		int counterSpace = 0;

		for (int w = left; w <= right; w++)
		{
			for (int h = top; h <= bottom; h++)
			{
				if(imageTool.makeRGB(img.getRGB(w, h)) == 0){
					if(counterSpace != 0 && counterSpace < 100){
						space[counterSpace] = space[counterSpace] + 1;
					}
					counterSpace = 0;
					counterDie++;
				}else {
					if(counterDie != 0 && counterDie < 100){
						die[counterDie] = die[counterDie] + 1;
					}
					counterSpace++;
					counterDie = 0;
				}
			}
			counterDie = 0;
			counterSpace = 0;
		}
		for(int i = 0; i < 100; i++){
			if(die[i] > die[dieHeight]){
				dieHeight = i;
			}
			if(space[i] > space[spaceHeight]){
				spaceHeight = i;
			}
		}
		counterDie = 0;
		counterSpace = 0;
		for (int h = top; h <= bottom; h++)
		{
			for (int w = left; w <= right; w++)
			{
				if(imageTool.makeRGB(img.getRGB(w, h)) == 0){
					if(counterSpace != 0 && counterSpace < 100){
						space[counterSpace] = space[counterSpace] + 1;
					}
					counterSpace = 0;
					counterDie++;
				}else {
					if(counterDie != 0 && counterDie < 100){
						die[counterDie] = die[counterDie] + 1;
					}
					counterSpace++;
					counterDie = 0;
				}
			}
			counterDie = 0;
			counterSpace = 0;
		}

		for(int i = 0; i < 100; i++){
			if(die[i] > die[dieWidth]){
				dieWidth = i;
			}
			if(space[i] > space[spaceWidth]){
				spaceWidth = i;
			}
		}
		int[] res = new int[2];
		res[0] = dieWidth + spaceWidth;
		res[1] = dieHeight + spaceHeight;
		return res;
	}

}
