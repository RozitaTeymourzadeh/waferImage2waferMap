/**
 * 
 */
package cs601;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FileOperation Class
 * To Find layout boundary by calculating the index start and end point of wafermap
 * 
 * @author rozitateymourzadeh
 *
 */
public class FileOperation {
	private static Logger LOG = LogManager.getLogger(FileOperation.class);
	private int maxDiodePerLine = Integer.parseInt(ConfigManager.getConfig().getMaxDiodePerLine());
	private int iStart = 0;
	private int iEnd = maxDiodePerLine - 1;
	private int jStart = 0;
	private int jEnd = maxDiodePerLine - 1;
	private boolean[][] waferMap;
	
	/**
	 * Constructor
	 */
	public FileOperation(boolean [][] waferMap) {
		this.waferMap = waferMap;
	}
	/**
	 * indexCalculator 
	 * 
	 * Calculate iStart/iEnd and jStart/jEnd
	 * 
	 */
	public void indexCalculator () {
		LOG.info("Index Calculator is Started!");
		for(int i = iStart; i < maxDiodePerLine && iStart == 0; i++){
			for(int j = jStart; j <= jEnd; j++){
				if(waferMap[i][j]){
					iStart = i;
					break;
				}
			}
		}
		for(int i = iEnd; i > iStart && iEnd == maxDiodePerLine - 1; i--){
			for(int j = jStart; j <= jEnd; j++){
				if(waferMap[i][j]){
					iEnd = i;
					break;
				}
			}
		}
		for(int j = jStart; j < maxDiodePerLine && jStart == 0; j++){
			for(int i = iStart; i <= iEnd; i++){
				if(waferMap[i][j]){
					jStart = j;
					break;
				}
			}
		}
		for(int j = jEnd - 1; j > jStart && jEnd == maxDiodePerLine - 1; j--){
			for(int i = iStart; i <= iEnd; i++){
				if(waferMap[i][j]){
					jEnd = j;
					break;
				}
			}
		}
	}
	
	
	/**
	 * Getter and Setter
	 */
	public int getiStart() {
		return iStart;
	}
	public void setiStart(int iStart) {
		this.iStart = iStart;
	}
	public int getiEnd() {
		return iEnd;
	}
	public void setiEnd(int iEnd) {
		this.iEnd = iEnd;
	}
	public int getjStart() {
		return jStart;
	}
	public void setjStart(int jStart) {
		this.jStart = jStart;
	}
	public int getjEnd() {
		return jEnd;
	}
	public void setjEnd(int jEnd) {
		this.jEnd = jEnd;
	}
}
