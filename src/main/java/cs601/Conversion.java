/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Conversion Class 
 * To do image conversion 
 * 
 * @author rozitateymourzadeh
 *
 */
public class Conversion {
	private static Logger LOG = LogManager.getLogger(Conversion.class);
	BufferedImage image = null;
	ScanImage scnImg = new ScanImage();
	
	public Conversion(){
		
	}
	
	public void convert(File imageFile) {
		
		String prefix = imageFile.getAbsolutePath();
		String imageName = imageFile.getName(); 
		int[] state = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		ImageProcessingTools imageTool = new ImageProcessingTools();
		Filter filter = new Filter();
		Measurement msrt = new Measurement();
		LayoutGenerator layout = new LayoutGenerator();
		if(imageName.indexOf(".") > 0) {
			imageName = imageName.substring(0, imageName.lastIndexOf(".") );
		}
		try {
			image = ImageIO.read(imageFile);
			LOG.info("Start processing image: \"" + imageName + "\" ......");
		} catch(IOException e) {
			LOG.error("FATAL: Failed to read the image: " + imageFile.getAbsolutePath());
			return;
		}

		int height = image.getHeight();
		int width = image.getWidth();
		LOG.info("Image Height and Width is: " + height + " x " + width);

		// Convert Image to black and white image
		state = imageTool.createBlackWhite(image, height, width);
		// Normalize RGB to Black and White
		image = imageTool.normalizedBlackWhite(image, height, width);
		int[] dieSize = msrt.calcSize(image, 0, image.getWidth() - 1, 0, image.getHeight() - 1);
		for(int i = 0; i<2; i++)
		{
			LOG.info("Die size is :" + dieSize[i]);
		}

		int startLine = scnImg.findTopDiode(image, dieSize);
		int endLine = scnImg.findBottomDiode(image, dieSize, startLine);
		int leftLine = scnImg.findLeftDiode(image, dieSize);
		int rightLine = scnImg.findRightDiode(image, dieSize, leftLine);

		leftLine = leftLine - dieSize[0];
		rightLine = rightLine + dieSize[0];
		startLine = startLine - dieSize[1];
		endLine = endLine + dieSize[1];

		leftLine = leftLine >=0 ? leftLine : 0;
		rightLine = rightLine < image.getWidth() ? rightLine : image.getWidth() - 1;
		startLine = startLine >=0 ? startLine : 0;
		endLine = endLine < image.getHeight() ? endLine : image.getHeight() - 1;

		width = rightLine - leftLine + 1;
		height = endLine - startLine + 1;
		image = filter.cropFilter(image, prefix, height, width, startLine, endLine, leftLine, rightLine);
		// To calculate and print Die size
		layout.printLayout(image, prefix, imageName, height, width, dieSize);
		LOG.info("Done!");
	}
}
