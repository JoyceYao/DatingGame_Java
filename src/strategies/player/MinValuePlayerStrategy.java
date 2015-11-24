package strategies.player;

import game.StrategyService;

import java.util.Arrays;
import java.util.Random;

public class MinValuePlayerStrategy extends PlayerStrategy {
	
	Random r = new Random();
	float[] origin;

	@Override
	public float[] getInitialWeights() {
		origin = new float[attributeNo];
		int countPositive = attributeNo/2;
		float posiValue = (float)1/countPositive;
		
		int countNegitive = attributeNo-countPositive;
		float negiValue = (float)-1/countNegitive;
		
		float positiveSum = 0;
		float negativeSum = 0;
		
		for(int i=0; i<countPositive; i++){
			origin[i] = posiValue;
			positiveSum += StrategyService.roundByTwoDigit(posiValue);
		}
		
		for(int i=countPositive; i<attributeNo; i++){
			origin[i] = negiValue;
			negativeSum += StrategyService.roundByTwoDigit(negiValue);
		}
		
		float posiDiff = (float)1-positiveSum;
		float negiDiff = ((float)1+negativeSum)*-1;
		
		for(int i=0; i<attributeNo; i++){
			if(origin[i] > 0){
				origin[i] += posiDiff;
				break;
			}
		}
		
		for(int i=0; i<attributeNo; i++){
			if(origin[i] < 0){
				origin[i] += negiDiff;
				break;
			}
		}
		StrategyService.printArray(origin);
		
		StrategyService.shuffleArray(origin);
		
		StrategyService.printArray(origin);
		return origin;
	}

	@Override
	public float[] getNextWeights() {
		//return origin;
		System.out.println("getNextWeights!");
		return StrategyService.RandomModifyWeight(origin, attributeNo);
	}

}
