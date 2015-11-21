package strategies.player;

import game.StrategyService;

import java.util.Arrays;
import java.util.Random;

public class RandomPlayerStrategy extends PlayerStrategy {
	
	Random r = new Random();
	float[] origin;

	@Override
	public float[] getInitialWeights() {
		origin = StrategyService.getRandomWeight(attributeNo);
		return origin;
	}

	@Override
	public float[] getNextWeights() {
		//return getRandomWeight();
		return origin;
	}
	

	
	/* 
	 * 
	 * /
	private float[] RandomModifyWeight(){
		int modifyNo = (int)Math.floor((double)attributeNo*0.05);
		float[] result = Arrays.copyOf(origin, origin.length);
		while(modifyNo > 0){
			int idx = r.nextInt(attributeNo);
			if(result[idx] == origin[idx] && result[idx] != 0){
				float modifyRate = r.nextFloat()/5;
				boolean isAdd = r.nextFloat() > 0.5? true : false;
				if(isAdd){
					result[idx] += result[idx]*modifyRate;
				}else{
					result[idx] -= result[idx]*modifyRate;
				}
			}
			
		}
	}*/

	private void shuffleArray(float[] ar){
		for (int i = ar.length - 1; i > 0; i--){
			int index = r.nextInt(i + 1);
			float a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
	
	private void printArray(float[] input){
		for(float f: input){
			System.out.print(f + " ");
		}
		System.out.println();
	}
}
