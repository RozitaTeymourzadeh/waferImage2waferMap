/**
 * 
 */
package cs601.Tool;

import java.awt.image.BufferedImage;
import java.io.IOException;

import cs601.Filter.Filter;
import cs601.Service.ConfigManager;

/**
 * @author Rozita Teymourzaedeh
 *
 */
public class ScanImage {
	private Integer dieDistanceTolerance = Integer.parseInt(ConfigManager.getConfig().getDieDistanceTolerance());
	private float dieSizeThr = Float.parseFloat(ConfigManager.getConfig().getDieSizeThr());
	private Filter filter = new Filter();
	
	/*
	 * Constructor
	 **/
	public ScanImage() {
		
	}
	
	/**
	 * 
	 * findTopDiode
	 * Find startLine from top part of the image
	 * @param image
	 * @param dieSize
	 * @return startLine
	 */
	public int findTopDiode(BufferedImage image, int[] dieSize) throws IOException {

		int startLine = 0;
		int lineCounter = 0;
		for (int h = startLine; h < image.getHeight(); h++)
		{
			int pixelCounter = 0;
			for (int w = 1; w < image.getWidth(); w++)
			{
				if(filter.makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (dieSizeThr * dieSize[1])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == dieDistanceTolerance ){
				startLine = h - dieDistanceTolerance  - 1;
				break;
			}
		}
		return startLine;
	}
	
	/**
	 * 
	 * find Bottom line of Image 
	 * 
	 * @param image
	 * @param dieSize
	 * @param startLine
	 * @return endLine
	 */
	public int findBottomDiode(BufferedImage image, int[] dieSize, int startLine) throws IOException{

		int endLine = image.getHeight() - 1;
		int lineCounter = 0;
		for (int h = endLine; h > startLine ; h--)
		{
			int pixelCounter = 0;
			for (int w = 1; w < image.getWidth(); w++)
			{
				if(filter.makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (dieSizeThr * dieSize[1])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == dieDistanceTolerance ){
				endLine = h + dieDistanceTolerance  + 1;
				break;
			}
		}
		return endLine;
	}
	
	/**
	 * find Left line of Image 
	 * 
	 * @param image
	 * @param dieSize
	 * @return leftLine
	 */
	public int findLeftDiode(BufferedImage image, int[] dieSize) throws IOException{

		int leftLine = 0;
		int lineCounter = 0;
		for (int w = leftLine; w < image.getWidth(); w++)
		{
			int pixelCounter = 0;
			for (int h = 0; h < image.getHeight() ; h++)
			{
				if(filter.makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (dieSizeThr * dieSize[1])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == dieDistanceTolerance ){
				leftLine = w - dieDistanceTolerance  - 1;
				break;
			}
		}
		return leftLine;
	}

	/**
	 * find Right line of Image
	 * @param image
	 * @param dieSize
	 * @param leftLine
	 * @return rightLine
	 */
	public int findRightDiode(BufferedImage image, int[] dieSize, int leftLine) throws IOException{
		int rightLine = image.getWidth() - 1;
		int lineCounter = 0;
		for (int w = rightLine; w > leftLine; w--)
		{
			int pixelCounter = 0;
			for (int h = 0; h < image.getHeight() ; h++)
			{
				if(filter.makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (dieSizeThr * dieSize[0])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == dieDistanceTolerance ){
				rightLine = w + dieDistanceTolerance  + 1;
				break;
			}
		}
		return rightLine;
	}
}
