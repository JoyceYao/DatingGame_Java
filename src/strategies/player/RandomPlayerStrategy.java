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
		return origin;
	}

}
