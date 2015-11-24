package strategies.player;

import game.StrategyService;

import java.util.Arrays;
import java.util.Random;

public class LotsOfPositivePlayerStrategy extends PlayerStrategy {
	
	Random r = new Random();
	float[] origin;

	@Override
	public float[] getInitialWeights() {

		origin = StrategyService.getMostPositiveRandomWeight(attributeNo);
		
		StrategyService.printArray(origin);
		
		System.out.println("Valid check!" + StrategyService.isValidWeight(origin));
		
		//StrategyService.shuffleArray(origin);
		
		//StrategyService.printArray(origin);
		return origin;
	}

	@Override
	public float[] getNextWeights() {
		//return origin;
		System.out.println("getNextWeights!");
		origin = StrategyService.RandomModifyWeight(origin, attributeNo);
		return origin;
	}

}
