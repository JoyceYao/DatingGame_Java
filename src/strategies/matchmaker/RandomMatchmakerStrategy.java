package strategies.matchmaker;

import java.util.Random;

public class RandomMatchmakerStrategy extends MatchmakerStrategy {
	
	Random r = new Random();

	@Override
	public float[] getFirstGuess(float[][] attributes, float[] scores) {
		return getRandomGuess();
	}

	@Override
	public float[] getNextGuess(float score) {
		return getRandomGuess();
	}
	
	private float[] getRandomGuess(){
		float[] result = new float[attributeNo];
		for(int i=0; i<attributeNo; i++){
			result[i] = r.nextFloat();
		}
		return result;
	}

}
