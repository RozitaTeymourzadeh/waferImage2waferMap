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
		LOG.info("Image Height and Width is: " + height + " " + width);
		
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

				int r = (rgb >> 16) & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb & 0xFF);

				statR[r]++;
				statG[g]++;
				statB[b]++;

				stat[makeRGB(rgb)]++;
			}
		}
	}




	private static int makeRGB(int rgb) {
		// TODO Auto-generated method stub
		return 0;
	}
}
