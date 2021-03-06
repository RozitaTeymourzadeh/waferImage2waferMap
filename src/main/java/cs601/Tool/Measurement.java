/**
 * 
 */
package cs601.Tool;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cs601.Filter.Filter;
import cs601.Service.ConfigManager;

/**
 * Measurement Class
 * A class to conduct measurement on the selected die
 * 
 * @author Rozita Teymourzadeh
 *
 */
public class Measurement {
	private static Logger LOG = LogManager.getLogger(Measurement.class);
	private Integer avgComponent = Integer.parseInt(ConfigManager.getConfig().getAveComponant());
	private Filter filter = new Filter();
	
	/*
	 * Constructor
	 * 
	 **/
	public Measurement() {
		
	}
	
	/**
	 * calcSize
	 * To calculate die size
	 * 
	 * @param left
	 * @param right
	 * @param top
	 * @param Bottom
	 * @throws IOException
	 */
	public int[] calcSize(BufferedImage img, int left, int right, int top, int bottom) throws IOException{
		LOG.info("Die Size calculation is started!");
		int dieHeight = 0;
		int spaceHeight = 0;
		int dieWidth = 0;
		int spaceWidth = 0;
		int[] die = new int[avgComponent];
		int[] space = new int[avgComponent];

		int counterDie = 0;
		int counterSpace = 0;

		for (int w = left; w <= right; w++)
		{
			for (int h = top; h <= bottom; h++)
			{
				if(filter.makeRGB(img.getRGB(w, h)) == 0){
					if(counterSpace != 0 && counterSpace < avgComponent){
						space[counterSpace] = space[counterSpace] + 1;
					}
					counterSpace = 0;
					counterDie++;
				}else {
					if(counterDie != 0 && counterDie < avgComponent){
						die[counterDie] = die[counterDie] + 1;
					}
					counterSpace++;
					counterDie = 0;
				}
			}
			counterDie = 0;
			counterSpace = 0;
		}
		for(int i = 0; i < avgComponent; i++){
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
				if(filter.makeRGB(img.getRGB(w, h)) == 0){
					if(counterSpace != 0 && counterSpace < avgComponent){
						space[counterSpace] = space[counterSpace] + 1;
					}
					counterSpace = 0;
					counterDie++;
				}else {
					if(counterDie != 0 && counterDie < avgComponent){
						die[counterDie] = die[counterDie] + 1;
					}
					counterSpace++;
					counterDie = 0;
				}
			}
			counterDie = 0;
			counterSpace = 0;
		}

		for(int i = 0; i < avgComponent; i++){
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
