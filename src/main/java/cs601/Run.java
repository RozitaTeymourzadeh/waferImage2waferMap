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
 * @author rozitateymourzadeh
 *
 */
public class Run {
	private static Logger LOG = LogManager.getLogger(Run.class);
	private static File cacheFolder = null;

	public static void main(String[] args) {

		LOG.info("Convesion is started ..." + Run.class.getName());
		ConfigManager.getConfig().getInput();

		File folder = new File(ConfigManager.getConfig().getInput());//Read the waferIMG file
		cacheFolder = new File(folder.getParent(), "cache");
		// create cache file
		try {
			cacheFolder.mkdir();
			LOG.info("Create cache folder:" + cacheFolder.getAbsolutePath());
		} catch(Exception e) {
			LOG.error("FATAL: Exception occured while generating cache file in: " + Run.class.getName());
		}

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
			if(imgsProcessed[i1].getName().toLowerCase().contains("crop") || imgsProcessed[i1].getName().toLowerCase().contains("patterns")) {
				imgsProcessed[i1].delete();
			}
		}
		LOG.info("Conversion Process was completed!!");
	}




	private static void convert(File imageFile) {
		BufferedImage image = null;
		String prefix = imageFile.getAbsolutePath();
		String fileName = imageFile.getName(); 
		// get rid of .jpg
		if(fileName.indexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf(".") );
		}
		try {
			image = ImageIO.read(imageFile);
			LOG.info("Processing" + imageFile.getAbsolutePath());
		} catch(IOException e) {
			LOG.error("Failed in" + imageFile.getAbsolutePath());
			return;

		}

		int height = image.getHeight();
		int width = image.getWidth();
		int hStep = 1;
		int wStep = 1;
		int rqb;
		LOG.info("Image Height and Width is: " + height + " x " + width);

		int[] stat = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statR = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statG = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int[] statB = new int[Integer.parseInt(ConfigManager.getConfig().getGrayScale())];
		int rgb;
		/* ---------- Calculate  Black/White Threshold ---------- */
		for (int h = 1; h < height; h += hStep)
		{
			for (int w = 1; w < width; w += wStep)
			{
				rgb = image.getRGB(w, h);
				//mask rgb and separate the index
				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);

				statR[r]++;
				statG[g]++;
				statB[b]++;

				stat[makeRGB(rgb)]++;
			}
		}

		int max = 0;
		int maxLimit = Integer.parseInt(ConfigManager.getConfig().getGrayScale());
		for(int i = 0; i < maxLimit; i++){
			if(stat[i] > stat[max])
				max = i;
		}

		/*  ---------- To Print RGB State ---------- */ 
		for(int i = 0; i < maxLimit; i++){
			LOG.info("RGB states are: " + stat[i] + "	" + statR[i] + "	" + statG[i] + " " + statB[i]);
		}
		
		/*  ---------- Normalize RGB to Black and White ---------- */
		for (int h = 1; h < height; h+=hStep)
		{
			for (int w = 1; w<width; w+=wStep)
			{
				rgb = image.getRGB(w, h);

				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);

				rgb = makeRGB(rgb);

				for(int x = 0; x < wStep && w+x < width; x++){
					for(int y = 0; y < hStep && h+y < height; y++){
						int value = image.getRGB(w+x, h+y);
						rgb += makeRGB(value);
					}
				}
				rgb /= (wStep * wStep);
				// b as blue component to detect b/w cropped image
				if(b < 40){
					rgb = 0;
				} else {
					rgb = 0xFFFFFF;
				}
				image.setRGB(w / wStep, h / hStep, rgb);
			}
		}
		
		int[] dieSize = calcSize(image, 0, image.getWidth() - 1, 0, image.getHeight() - 1);
		// th, tolerance are Die size parameters
		float th = 1.5f;
		int tolerance = 5;// defined distance btw 2 dies, 

		/* ----------find top---------- */
		int startLine = 0;
		int lineCounter = 0;
		for (int h = startLine; h < image.getHeight(); h++)
		{
			int pixelCounter = 0;
			for (int w = 1; w < image.getWidth(); w++)
			{
				if(makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (th * dieSize[1])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == tolerance){
				startLine = h - tolerance - 1;
				break;
			}
		}
		
		/* ----------find left---------- */

		int leftLine = 0;
		lineCounter = 0;
		for (int w = leftLine; w < image.getWidth(); w++)
		{
			int pixelCounter = 0;
			for (int h = 0; h < image.getHeight() ; h++)
			{
				if(makeRGB(image.getRGB(w, h)) == 0){
					pixelCounter++;
				}
			}
			if(pixelCounter > (th * dieSize[1])){
				lineCounter++;
			} else{
				lineCounter = 0;
			}

			if(lineCounter == tolerance){
				leftLine = w - tolerance - 1;
				break;
			}
		}
	}


	private static int[] calcSize(BufferedImage image, int i, int j, int k, int l) {
		// TODO Auto-generated method stub
		return null;
	}




	/**
	 * makeRGB
	 * Generate RGB from r, g, b index
	 *
	 *@param rgb
	 *@return RGB
	 */
	private static int makeRGB(int rgb) {
		float r = (rgb >> 16) & 0xFF;
		float g = (rgb >> 8) & 0xFF;
		float b = (rgb & 0xFF);
		return (int)(r*0.299 + g*0.587 + b*0.114);
	}
}
