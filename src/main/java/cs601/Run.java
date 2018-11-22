/**
 * 
 */
package cs601;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rozitateymourzadeh
 *
 */
public class Run {
	private static Logger LOG = LogManager.getLogger(Run.class);
	private static File cacheFolder = null;
	private static boolean[][] waferMap;

	public static void main(String[] args) {

		Service srv = new Service();

		LOG.info("Convesion is started ..." + Run.class.getName());
		File folder = new File(ConfigManager.getConfig().getInput());//Read the waferIMG file
		//		int hStep = Integer.parseInt(ConfigManager.getConfig().getHStep());
		//		int wStep = Integer.parseInt(ConfigManager.getConfig().getWStep());
		cacheFolder = new File(folder.getParent(), "cache");

		// create cache file
		srv.createCache(cacheFolder);
		// read file
		File[] imgs = folder.listFiles();
		for(int i = 0; i < imgs.length; i++){
			if(imgs[i].getName().toLowerCase().endsWith(".jpg") || imgs[i].getName().toLowerCase().endsWith(".bmp")|| imgs[i].getName().toLowerCase().endsWith(".png")){
				convert(imgs[i]);
			}
		}

		// Delet processed files
		File[] imgsProcessed  = folder.listFiles();
		for(int i1 = 0 ; i1 < imgsProcessed.length ; i1++) {
			if(imgsProcessed[i1].getName().toLowerCase().contains("crop") || imgsProcessed[i1].getName().toLowerCase().contains("pattern")) {				imgsProcessed[i1].delete();
			}
		}
		LOG.info("Conversion Process was completed!!");
	}

	private static void convert(File imageFile) {
		BufferedImage image = null;
		ScanImage scnImg = new ScanImage();
		String prefix = imageFile.getAbsolutePath();
		String imageName = imageFile.getName(); 
		int[] state = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		ImageProcessingTools imageTool = new ImageProcessingTools();
		Filter filter = new Filter();
		Service srv = new Service();
		// get rid of .jpg
		if(imageName.indexOf(".") > 0) {
			imageName = imageName.substring(0, imageName.lastIndexOf(".") );
		}
		try {
			image = ImageIO.read(imageFile);
			LOG.info("Start processing image: \"" + imageName + "\" ......");
		} catch(IOException e) {
			LOG.error("FATAL: Failed in processing image: " + imageFile.getAbsolutePath());
			return;
		}

		int height = image.getHeight();
		int width = image.getWidth();
		LOG.info("Image Height and Width is: " + height + " x " + width);

		int rgb;
		// Convert Image to black and white image
		state = imageTool.createBlackWhite(image, height, width);

		// find maximum index of state
		int max = 0;
		int maxLimit = Integer.parseInt(ConfigManager.getConfig().getGrayScale());
		for(int i = 0; i < maxLimit; i++){
			if(state[i] > state[max])
				max = i;
		}

		/*  ---------- Normalize RGB to Black and White ---------- */
		image = imageTool.normalizedBlackWhite(image, height, width);

		int[] dieSize = calcSize(image, 0, image.getWidth() - 1, 0, image.getHeight() - 1);
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

		/* ----------To calculate and print Die size----------*/ 	
		LayoutGenerator layout = new LayoutGenerator();
		layout.printLayout(image, prefix, imageName, height, width, dieSize);
		System.out.println("Done!");

	}























	private static int[] calcSize(BufferedImage img, int left, int right, int top, int bottom){
		ImageProcessingTools imageTool = new ImageProcessingTools();
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
