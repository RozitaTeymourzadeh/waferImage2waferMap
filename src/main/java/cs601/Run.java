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

		BufferedImage pattern = getPattern(dieSize);
		srv.savePNG(pattern, prefix +"_Pattern.png");
		float thrUp = Float.parseFloat(ConfigManager.getConfig().getThrUp());
		float thrDown = Float.parseFloat(ConfigManager.getConfig().getThrDown());
		float thrLeft = Float.parseFloat(ConfigManager.getConfig().getThrLeft());
		float thrRight = Float.parseFloat(ConfigManager.getConfig().getThrRight());

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
				sim[i][j] = getSimilarity(image, x+i, y+j, pattern);
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
					sim[i+1][j+1] = getSimilarity(image, x+i, y+j, pattern);
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

			boolean[] left = findLeft(image, pattern, x, y,thrLeft);
			boolean[] right = findRight(image, pattern, x, y, thrRight);
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
					sim[i+1][j+1] = getSimilarity(image, x+i, y+j, pattern);
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

			boolean[] right = findRight(image, pattern, x, y, thrRight);
			boolean[] left = findLeft(image, pattern, x, y, thrLeft);
			for(int i = 100 - 1, j = 150; i >= 0; i--, j--)
			{
				waferMap[index][j] = left[i];
			}
			for(int i = 0, j = 150; i < 100; i++, j++)
			{
				waferMap[index][j] = right[i];
			}

		}

		int iStart = 0;
		int iEnd = 300 - 1;
		int jStart = 0;
		int jEnd = 300 - 1;

		for(int i = iStart; i < 300 && iStart == 0; i++){
			for(int j = jStart; j <= jEnd; j++){
				if(waferMap[i][j]){
					iStart = i;
					break;
				}
			}
		}
		for(int i = iEnd; i > iStart && iEnd == 300 - 1; i--){
			for(int j = jStart; j <= jEnd; j++){
				if(waferMap[i][j]){
					iEnd = i;
					break;
				}
			}
		}
		for(int j = jStart; j < 300 && jStart == 0; j++){
			for(int i = iStart; i <= iEnd; i++){
				if(waferMap[i][j]){
					jStart = j;
					break;
				}
			}
		}
		for(int j = jEnd - 1; j > jStart && jEnd == 300 - 1; j--){
			for(int i = iStart; i <= iEnd; i++){
				if(waferMap[i][j]){
					jEnd = j;
					break;
				}
			}
		}

		try {

			File mapFile = new File(ConfigManager.getConfig().getOutput(), imageName +".txt");
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(mapFile));

			/* Create ASIC header*/
			output.write("DEVICE PD_Side-nz\r\n");
			output.write("ROWCNT " + (iEnd-iStart+1)+ "\r\n");
			output.write("COLCNT " + (jEnd-jStart+1)+ "\r\n");
			output.write("PASBIN 1\r\n");
			output.write("SKPBIN .\r\n");
			output.write("NULBIN _\r\n");
			output.write("REFBIN R\r\n");
			output.write("WAFDIA 8\r\n");
			output.write("XDIES1 0.500000\r\n");
			output.write("YDIES1 0.500000\r\n");
			int lineNum = 1;
			for(int i = iStart; i <= iEnd; i++, lineNum++){
				//output.write("MAP"+String.format("%03d", lineNum) +" ");
				for(int j = jStart; j <= jEnd; j++){
					output.write(waferMap[i][j]?'1':'.');
				}
				output.write("\r\n") ;	
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done!");

	}












	private static boolean[] findLeft(BufferedImage img, BufferedImage pattern, int x, int y, float thrL){
		boolean[] res = new boolean[100];
		int index = 100 - 1;
		for( ; index >= 0 && x >= 1; x -= pattern.getWidth(), index--){
			float[][] sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = getSimilarity(img, x+i, y+j, pattern);
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

			if(sim[xTemp][yTemp] > thrL){
				res[index] = true;
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
				res[index] = false;
			}
		}

		for(; index >= 0; index--){
			res[index] = false;
		}

		return res;
	}




	private static boolean[] findRight(BufferedImage img, BufferedImage pattern, int x, int y, float thrR){
		boolean[] res = new boolean[100];
		int index = 0;
		for( ; index < 100 && x < img.getWidth() - pattern.getWidth() - 1; x += pattern.getWidth(), index++){
			float[][] sim = new float[3][3];

			for(int i = -1; i <= 1; i++){
				for(int j = -1; j <= 1; j++){
					sim[i+1][j+1] = getSimilarity(img, x+i, y+j, pattern);
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

			if(sim[xTemp][yTemp] > thrR){
				res[index] = true;
				y += yTemp - 1;
				x += xTemp - 1;
			} else {
				res[index] = false;
			}
		}

		for(; index < 100; index++){
			res[index] = false;
		}

		return res;
	}


	static int cacheCounter = 0;
	private static float getSimilarity(BufferedImage img, int x, int y, BufferedImage pattern){
		Service srv = new Service();
		float diff = 0;

		int width = pattern.getWidth();
		int height = pattern.getHeight();

		BufferedImage cache = srv.map(width, height);

		int counter = 0;

		float diameter = (float) Math.sqrt((width*width + height*height)) / 2;
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				counter++;
				if((x+i) >= img.getWidth() || (y+j) >= img.getHeight() || img.getRGB(x+i, y+j) != pattern.getRGB(i, j)){
					diff += (float) Math.sqrt((width/2 - i)*(width/2 - i) + (height/2 - j)*(height/2 - j)) / diameter;
				} else{
				}
			}
		}

		float res = 1 - diff / counter;//(width * height);
		return res*res*res*res;
	}


	private static BufferedImage getPattern(int[] size){
		Service srv = new Service();
		BufferedImage res = srv.map(size[0], size[1]);
		for(int i = 0; i < size[0]; i++){
			for(int j = 0; j < size[1]; j++){
				if(i == 0 || i == size[0] - 1 || j == 0 || j == size[1] - 1){
					res.setRGB(i, j, 0xFFFFFF);
				} else {
					res.setRGB(i, j, 0);
				}
			}
		}
		return res;
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
