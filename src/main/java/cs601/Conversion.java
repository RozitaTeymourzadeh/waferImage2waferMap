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
 * @author Rozita Teymourzadeh
 *
 */
public class Conversion {
	private static Logger LOG = LogManager.getLogger(Conversion.class);
	private BufferedImage image = null;
	private int[] state = null;
	private ScanImage scnImg = new ScanImage();
	private ImageProcessingTools imageTool = new ImageProcessingTools();
	private Filter filter = new Filter();
	private Measurement msrt = new Measurement();
	private LayoutGenerator layout = new LayoutGenerator();
	
	/**
	 * Constructor
	 */
	public Conversion(){
		
	}
	
	public void convert(File imageFile) {
		LOG.info("Conversion Process is started!");
		try {
			String prefix = imageFile.getAbsolutePath();
			String imageName = imageFile.getName(); 
			this.setState(new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())]);
			if(imageName.indexOf(".") > 0) {
				imageName = imageName.substring(0, imageName.lastIndexOf(".") );
			}
			image = ImageIO.read(imageFile);
			LOG.info("Start processing image: \"" + imageName + "\" ......");
			int height = image.getHeight();
			int width = image.getWidth();
			LOG.info("Image Height and Width is: " + height + " x " + width);
			this.setState(imageTool.createBlackWhite(image, height, width));
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
			leftLine = leftLine >= 0 ? leftLine : 0;
			rightLine = rightLine < image.getWidth() ? rightLine : image.getWidth() - 1;
			startLine = startLine >= 0 ? startLine : 0;
			endLine = endLine < image.getHeight() ? endLine : image.getHeight() - 1;
			width = rightLine - leftLine + 1;
			height = endLine - startLine + 1;
			image = filter.cropFilter(image, prefix, height, width, startLine, endLine, leftLine, rightLine);
			layout.printLayout(image, prefix, imageName, height, width, dieSize);
			LOG.info("Image is converted!");
		} catch(IOException e) {
			LOG.error("FATAL: Failed to read the image: " + imageFile.getAbsolutePath());
			return;
		}
	}

	/**
	 * @return the state
	 */
	public int[] getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int[] state) {
		this.state = state;
	}
}
