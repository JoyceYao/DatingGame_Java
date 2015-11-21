package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategyService {
	
	static Random r = new Random();
	
	public static boolean isValidWeight(float[] weight){
		int positiveSum = 0;
		int negativeSum = 0;
		
		for(int i=0; i<weight.length; i++){
			if(weight[i] > 0){
				positiveSum += weight[i];
			}else{
				negativeSum += weight[i];
			}
		}
		
		if(positiveSum != 1 || negativeSum != -1){ return false; }
		return true;
	}
	
	public static boolean isValidModifiedWeight(float[] original, float[] modified){
		if(!isValidWeight(modified)){ return false; }
		List<Integer> changedIndex = new ArrayList<Integer>();
		
		for(int i=0; i<modified.length; i++){
			if(original[i] != modified[i]){
				changedIndex.add(i);
			}
		}
		
		if((float)changedIndex.size()*100/modified.length > 5){
			return false;
		}
		
		for(int i=0; i<changedIndex.size(); i++){
			int index = changedIndex.get(i);
			float diff = Math.abs(original[index] - modified[index]);
			if(diff*100/original[index] > 20){
				return false;
			}
		}
		
		return true;
	}
	
	public static float roundByTwoDigit(float input){
		return Math.round((float)100*input)/(float)100;
	}
	
	/* randomly create positive and negative values
	 * verify: positive number sum to 1, negative number sum to 0
	 * then shuffle it 
	 */
	public static float[] getRandomWeight(int attributeNo){
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
	
	public static float[] getNonZeroRandomWeight(int attributeNo){
		float[] result = new float[attributeNo];
		float positiveNo = r.nextInt((attributeNo)/2);
		float negativeNo = attributeNo-positiveNo;
		
		float positiveSum = 0;
		float negativeSum = 0;
		
		float pos = roundByTwoDigit((float)1/positiveNo);
		int idx = 0;	
		
		for(int i=idx; i<positiveNo; i++){
			result[i] = pos;
			positiveSum += pos;
			idx++;
		}
		
		float neg = roundByTwoDigit((float)-1/negativeNo); 
		float negEnd = idx + negativeNo;
		for(int i=idx; i<negEnd; i++){
			result[i] = neg;
			negativeSum += neg;
			idx++;
		}
		
		// add left over number to make sure the sum is 1 and -1
		float diffPositive = roundByTwoDigit((float)1-positiveSum);
		float diffNegative = roundByTwoDigit(((float)1+negativeSum)*-1);
		for(int i=0; i<attributeNo; i++){
			if(result[i] > 0 && result[i] + diffPositive <= 1){
				result[i] += diffPositive;
				break;
			}
		}
		
		for(int i=0; i<attributeNo; i++){
			if(result[i] < 0 && result[i] + diffNegative <= -1){
				result[i] += diffNegative;
				break;
			}
		}
		
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
	
	public static void shuffleArray(float[] ar){
		for (int i = ar.length - 1; i > 0; i--){
			int index = r.nextInt(i + 1);
			float a = ar[index];
			ar[index] = ar[i];
			ar[i] = a;
		}
	}
	
	public static void printArray(float[] input){
		for(float f: input){
			System.out.print(f + " ");
		}
		System.out.println();
	}
}
