package strategies.player;

import game.StrategyService;

import java.util.Arrays;
import java.util.Random;

public class RandomPlayerStrategy extends PlayerStrategy {
	
	Random r = new Random();
	float[] origin;

	@Override
	public float[] getInitialWeights() {
		origin = getRandomWeight();
		return origin;
	}

	@Override
	public float[] getNextWeights() {
		//return getRandomWeight();
		return origin;
	}
	
	/* randomly create positive and negative values
	 * verify: positive number sum to 1, negative number sum to 0
	 * then shuffle it 
	 */
	private float[] getRandomWeight(){
		float[] result = new float[attributeNo];
		float positiveNo = r.nextInt((attributeNo-1)/2);
		float negativeNo = r.nextInt((attributeNo-1)/2);
		
		float positiveSum = 0;
		float negativeSum = 0;
		
		float pos = StrategyService.roundByTwoDigit((float)1/positiveNo);
		int idx = 0;	
		
		for(int i=idx; i<positiveNo; i++){
			result[i] = pos;
			positiveSum += pos;
			idx++;
		}
		
		float neg = StrategyService.roundByTwoDigit((float)-1/negativeNo); 
		float negEnd = idx + negativeNo;
		for(int i=idx; i<negEnd; i++){
			result[i] = neg;
			negativeSum += neg;
			idx++;
		}
		
		result[idx++] = StrategyService.roundByTwoDigit((float)1-positiveSum);
		result[idx++] = StrategyService.roundByTwoDigit(((float)1+negativeSum)*-1);
		
		System.out.print("getRandomWeight[0] result = ");
		printArray(result);
		shuffleArray(result);
		
		for(int i=0; i<50; i++){
			int idx1 = r.nextInt(attributeNo);
			int idx2 = r.nextInt(attributeNo);
			float diff = StrategyService.roundByTwoDigit(r.nextFloat()/attributeNo);
			// choose two position with the same sign, reduce diff from one position and add to the other position
			if(result[idx1]*result[idx2] > 0){
				if((result[idx1] > 0 && result[idx1]-diff > 0 && result[idx2]+diff <= 1)
					|| (result[idx1] < 0 && result[idx1]-diff >= -1 && result[idx2]+diff <= 0)){
					result[idx1] -= diff;
					result[idx2] += diff;
				}
			}
		}
		
		System.out.print("getRandomWeight[1] result = ");
		printArray(result);
		return result;
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
