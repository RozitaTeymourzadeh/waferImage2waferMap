/**
 * 
 */
package cs601;

/**
 * @author rozitateymourzadeh
 *
 */
public class FileOperation {

	private int iStart = 0;
	private int iEnd = 300 - 1;
	private int jStart = 0;
	private int jEnd = 300 - 1;
	private boolean[][] waferMap;

	public FileOperation(boolean [][] waferMap) {
		this.waferMap = waferMap;
	}

	public void indexCalculator () {

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
	}	


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
