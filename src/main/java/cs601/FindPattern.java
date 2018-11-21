package cs601;

import java.awt.image.BufferedImage;

public class FindPattern {
	
	private float thrLeft = Float.parseFloat(ConfigManager.getConfig().getThrLeft());
	private float thrRight = Float.parseFloat(ConfigManager.getConfig().getThrRight());
	Service srv = new Service();
	
	public boolean[] findLeft(BufferedImage img, BufferedImage pattern, int x, int y){
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

			if(sim[xTemp][yTemp] > thrLeft){
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

	public boolean[] findRight(BufferedImage img, BufferedImage pattern, int x, int y){
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

			if(sim[xTemp][yTemp] > thrRight){
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
	
	public float getSimilarity(BufferedImage img, int x, int y, BufferedImage pattern){
		int cacheCounter = 0;
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
}
