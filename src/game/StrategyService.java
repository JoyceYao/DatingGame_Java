package game;

import java.util.ArrayList;
import java.util.List;

public class StrategyService {
	
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
}
