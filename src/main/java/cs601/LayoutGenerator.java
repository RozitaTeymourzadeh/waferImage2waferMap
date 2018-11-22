/**
 * 
 */
package cs601;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rozitateymourzadeh
 *
 */
public class LayoutGenerator {
	
	private int lineNum;
	
	public LayoutGenerator() {
		
	}
	
	public LayoutGenerator(int lineNum) {
		this.setLineNum(lineNum);
	}
	
	private static Logger LOG = LogManager.getLogger(LayoutGenerator.class);
	WaferMap wafer = new WaferMap();
	Service srv = new Service();
	PatternDetector patternDetector = new PatternDetector();

	/**
	 * printLayout
	 * To write wafer map in in text file
	 * 
	 * @param image
	 * @param path
	 * @param imageName
	 * @param height
	 * @param width
	 * @param dieSize
	 */
	public void printLayout(BufferedImage image, String path, String imageName, int height,int width, int[] dieSize) {
		boolean[][] waferMap = null ;
		try {
			BufferedImage pattern = patternDetector.getPattern(dieSize);
			srv.savePNG(pattern, path +"_Pattern.png");
			waferMap = wafer.createWaferMap(image, height, width, pattern);
			FileOperation fileOperation = new FileOperation(waferMap);
			File mapFile = new File(ConfigManager.getConfig().getOutput(), imageName +".txt");
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(mapFile));
			fileOperation.indexCalculator();
			/* Create ASIC header*/
			this.setLineNum(lineNum);
			this.header(dieSize, fileOperation, output);
			for(int i = fileOperation.getiStart(); i <= fileOperation.getiEnd(); i++, lineNum++){
				for(int j = fileOperation.getjStart(); j <= fileOperation.getjEnd(); j++){
					output.write(waferMap[i][j] ? '1' : '.');
				}
				output.write("\r\n") ;	
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			LOG.error("FATAL: Fail to generate waferlayout.");
		}
	}

	/**
	 * header
	 * Generate header file for wafer map
	 * 
	 * @param dieSize
	 * @param fileOperation
	 * @param output
	 * @throws IOException
	 */
	public void header(int[] dieSize, FileOperation fileOperation, BufferedWriter output) throws IOException {
		output.write("DEVICE PD_Side-nz\r\n");
		output.write("ROWCNT " + (fileOperation.getiEnd()-fileOperation.getiStart()+1)+ "\r\n");
		output.write("COLCNT " + (fileOperation.getjEnd()-fileOperation.getjStart()+1)+ "\r\n");
		output.write("PASBIN 1\r\n");
		output.write("SKPBIN .\r\n");
		output.write("NULBIN _\r\n");
		output.write("REFBIN R\r\n");
		output.write("WAFDIA 8\r\n");
		output.write("XDIES1 " + dieSize[0] + "\r\n");
		output.write("YDIES1 " + dieSize[1] + "\r\n");
	}

	/**
	 * Getter
	 * @return the lineNum
	 */
	public int getLineNum() {
		return lineNum;
	}

	/**
	 * Setter
	 * @param lineNum the lineNum to set
	 */
	public void setLineNum(int lineNum) {
		this.lineNum = 1;
	}

}
