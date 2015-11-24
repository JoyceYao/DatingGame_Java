package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StrategyService {
	
	static Random r = new Random();
	
	public static boolean isValidWeight(float[] weight){
		//System.out.println("isValidWeight[0]");
		float positiveSum = 0;
		float negativeSum = 0;
		
		for(int i=0; i<weight.length; i++){
			if(weight[i] > 0){
				positiveSum += roundByTwoDigit(weight[i]);
				//System.out.println("positiveSum="+ positiveSum);
			}else{
				negativeSum += roundByTwoDigit(weight[i]);
				//System.out.println("negativeSum="+ negativeSum);
			}
		}
		
		//System.out.println("isValidWeight positiveSum=" + positiveSum);
		//System.out.println("isValidWeight negativeSum=" + negativeSum);
		
		if(roundByTwoDigit(positiveSum) != 1 || roundByTwoDigit(negativeSum) != -1){ return false; }
		
		//System.out.println("isValidWeight[2]");
		return true;
	}
	
	public static boolean isValidModifiedWeight(float[] original, float[] modified){
		System.out.println("isValidModifiedWeight[0]");
		
		if(!isValidWeight(modified)){ return false; }
		
		System.out.println("isValidModifiedWeight[1]");
		
		List<Integer> changedIndex = new ArrayList<Integer>();
		
		for(int i=0; i<modified.length; i++){
			if(original[i] != modified[i]){
				changedIndex.add(i);
			}
		}
		
		
		if((float)changedIndex.size()*100/modified.length > 5){
			return false;
		}
		
		System.out.println("isValidModifiedWeight[2]");
		
		for(int i=0; i<changedIndex.size(); i++){
			int index = changedIndex.get(i);
			float diff = Math.abs(original[index] - modified[index]);
			if(diff*100/original[index] > 20){
				return false;
			}
		}
		
		System.out.println("isValidModifiedWeight[3]");
		
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
		float positiveNo = r.nextInt(attributeNo-1);
		float negativeNo = attributeNo-positiveNo;
		
		float positiveSum = 0;
		float negativeSum = 0;
		
		float pos = roundByTwoDigit((float)1/positiveNo);
		
		for(int i=0; i<positiveNo; i++){
			result[i] = pos;
			positiveSum += pos;
		}
		
		float neg = roundByTwoDigit((float)-1/negativeNo); 
		for(int i=(int)positiveNo; i<attributeNo; i++){
			result[i] = neg;
			negativeSum += neg;
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
			if(result[i] < 0 && result[i] + diffNegative >= -1){
				result[i] += diffNegative;
				break;
			}
		}
		
		//System.out.print("getRandomWeight[0] result = ");
		//printArray(result);
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
		
		//System.out.print("getRandomWeight[1] result = ");
		//printArray(result);
		return result;
	}
	
	public static float[] getMostPositiveRandomWeight(int attributeNo){
		float[] result = new float[attributeNo];
		int half = attributeNo/2;
		float positiveNo = half+r.nextInt(attributeNo/2-1);
		float negativeNo = attributeNo-positiveNo;

		float pos = (float)1/positiveNo;
		for(int i=0; i<positiveNo; i++){
			result[i] = pos;
		}
		
		float neg = (float)-1/negativeNo; 
		for(int i=(int)positiveNo; i<attributeNo; i++){
			result[i] = neg;
		}
		
		//System.out.print("getRandomWeight[0] result = ");
		//printArray(result);
		shuffleArray(result);
		
		for(int i=0; i<50; i++){
			int idx1 = r.nextInt(attributeNo);
			int idx2 = r.nextInt(attributeNo);
			float diff = r.nextFloat()/attributeNo;
			// choose two position with the same sign, reduce diff from one position and add to the other position
			if(result[idx1]*result[idx2] > 0){
				if((result[idx1] > 0 && result[idx1]-diff > 0 && result[idx2]+diff <= 1)
					|| (result[idx1] < 0 && result[idx1]-diff >= -1 && result[idx2]+diff <= 0)){
					//if(roundByTwoDigit(result[idx1]-diff) == roundByTwoDigit(result[idx1])){ continue; }
					result[idx1] -= diff;
					result[idx2] += diff;
				}
			}
		}
		
		printArray(result);
		
		for(int i=0; i<attributeNo; i++){
			result[i] = roundByTwoDigit(result[i]);
		}
		
		printArray(result);
		
		float positiveSum = 0;
		float negativeSum = 0;
		for(int i=0; i<attributeNo; i++){
			if(result[i] > 0){ positiveSum += result[i]; }
			else{ negativeSum += result[i]; }
		}
		
		// add left over number to make sure the sum is 1 and -1
		float diffPositive = roundByTwoDigit((float)1-positiveSum);
		float diffNegative = roundByTwoDigit(((float)1+negativeSum)*-1);
		
		System.out.println("getMostPositiveRandomWeight isValid[0]=" + isValidWeight(result));
		
		for(int i=0; i<attributeNo; i++){
			if(result[i] > 0 && result[i] + diffPositive <= 1){
				result[i] += diffPositive;
				break;
			}
		}
		
		for(int i=0; i<attributeNo; i++){
			if(result[i] < 0 && result[i] + diffNegative >= -1){
				result[i] += diffNegative;
				break;
			}
		}
		
		System.out.println("getMostPositiveRandomWeight isValid[1]=" + isValidWeight(result));

		//System.out.print("getRandomWeight[1] result = ");
		//printArray(result);
		return result;
	}
	
	/*  input: origin array
	 *  output: randomly modified array, 
	 *  	modified elements < 5%
	 *  	modified value < +-20%
	 */
	public static float[] RandomModifyWeight(float[] origin, int attributeNo){
		System.out.println("RandomModifyWeight[0]");
		
		int modifyNo = (int)Math.floor((double)attributeNo*0.05);
		if(modifyNo < 2){ return origin; }
		
		
		float[] result = Arrays.copyOf(origin, origin.length);
		int idx1 = r.nextInt(attributeNo);
		while(result[idx1] == 0){ idx1 = r.nextInt(attributeNo); }
			
		float modifyValue = result[idx1]/5;
		boolean isAdd = r.nextFloat() > 0.5? true : false;
		if(isAdd){
			result[idx1] += modifyValue;
		}else{
			result[idx1] -= modifyValue;
		}
		result[idx1] = StrategyService.roundByTwoDigit(result[idx1]);
		if(result[idx1] == origin[idx1]){ return origin; } 
			
		int idx2 = r.nextInt(attributeNo);
		while(result[idx2] == 0 || idx2 == idx1 || result[idx1]*result[idx2] < 0 || result[idx2]/5 < modifyValue){ 
			idx2 = r.nextInt(attributeNo); 
		}
			
		if(!isAdd){
			result[idx2] += modifyValue;
		}else{
			result[idx2] -= modifyValue;
		}
		
		if(isValidModifiedWeight(origin, result)){
			return result;
		}else{
			System.out.println("Problems in modify logic!!!");
			return origin;
		}
		
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
