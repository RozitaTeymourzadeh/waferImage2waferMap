/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;

/**
 * @author rozitateymourzadeh
 *
 */
public class ScanImage {
	Integer dieDistanceTolerance = Integer.parseInt(ConfigManager.getConfig().getDieDistanceTolerance());
	float dieSizeThr = Float.parseFloat(ConfigManager.getConfig().getDieSizeThr());
	ImageProcessingTools imageTool = new ImageProcessingTools();
	
	/**
	 * 
	 * findTopDiode
	 * Find startLine from top part of the image
	 * @param image
	 * @param dieSize
	 * @return startLine
	 */
	public int findTopDiode(BufferedImage image, int[] dieSize) {

		int startLine = 0;
		int lineCounter = 0;
		for (int h = startLine; h < image.getHeight(); h++)
		{
			int pixelCounter = 0;
			for (int w = 1; w < image.getWidth(); w++)
			{
				if(imageTool.makeRGB(image.getRGB(w, h)) == 0){
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
	public int findBottomDiode(BufferedImage image, int[] dieSize, int startLine) {

		int endLine = image.getHeight() - 1;
		int lineCounter = 0;
		for (int h = endLine; h > startLine ; h--)
		{
			int pixelCounter = 0;
			for (int w = 1; w < image.getWidth(); w++)
			{
				if(imageTool.makeRGB(image.getRGB(w, h)) == 0){
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
	 * @param image
	 * @param imageTool
	 * @param dieSize
	 * @param dieSizeThr
	 * @param dieDistanceTolerance
	 * @return
	 */
	public int finfLeftDiode(BufferedImage image, int[] dieSize) {

		int leftLine = 0;
		int lineCounter = 0;
		for (int w = leftLine; w < image.getWidth(); w++)
		{
			int pixelCounter = 0;
			for (int h = 0; h < image.getHeight() ; h++)
			{
				if(imageTool.makeRGB(image.getRGB(w, h)) == 0){
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



}
