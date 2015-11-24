package strategies.matchmaker;

import game.StrategyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MinErrorMatchmakerStrategy extends MatchmakerStrategy {
	
	Random r = new Random();
	List<ResultContainer> myGuessList = new ArrayList<ResultContainer>();
	List<ResultContainer> resultList = new ArrayList<ResultContainer>();
	float[][] firstResult;
	float[] myPevGuess;

	@Override
	public float[] getFirstGuess(float[][] attributes, float[] scores) {
		
		for(int i=0; i<attributes.length; i++){
			myGuessList.add(new ResultContainer(attributes[i], scores[i]));
		}
		
		for(int k=0; k<10; k++){
			for(int i=0; i<1000; i++){
				float[] thisPredict = StrategyService.getNonZeroRandomWeight(attributeNo);
				float error = 0;
				for(int j=0; j<attributes.length; j++){
					error += getErrorDiff(getScore(attributes[j], thisPredict), scores[j]);
				}
				resultList.add(new ResultContainer(thisPredict, error));
			}
			Collections.sort(resultList);
			resultList = resultList.subList(0, 200);
			
			for(int i=0; i<1000; i++){
				float[] thisPredict = getMutation(resultList.get(r.nextInt(5)).result);
				float error = 0;
				for(int j=0; j<attributes.length; j++){
					error += getErrorDiff(getScore(attributes[j], thisPredict), scores[j]);
				}
				resultList.add(new ResultContainer(thisPredict, error));
			}
			Collections.sort(resultList);
			resultList = resultList.subList(0, 200);
		}
		
		Collections.sort(resultList);
		
		float[] voting = new float[attributeNo];
		for(int i=0; i<1; i++){
			float[] thisResult = resultList.get(i).result;
			System.out.println("thisResult =");
			StrategyService.printArray(thisResult);
			
			for(int j=0; j<attributeNo; j++){
				if(thisResult[j] > 0){
					voting[j]++;
				}else if(thisResult[j] < 0){
					voting[j]--;
				}
			}
		}
		System.out.println("Voting result[1]=");
		StrategyService.printArray(voting);
		
		for(int i=0; i<attributeNo; i++){
			if(voting[i] > 0){
				voting[i] = 1;
			}else{
				voting[i] = 0;
			}
		}
		System.out.println("Voting result[2]=");
		StrategyService.printArray(voting);
		myPevGuess = voting;
		firstResult= attributes;
		
		return voting;
	}

	@Override	
	public float[] getNextGuess(float score) {
		myGuessList.add(new ResultContainer(myPevGuess, score));
		
		for(int k=0; k<10; k++){
			for(int i=0; i<1000; i++){
				float[] thisPredict = StrategyService.getNonZeroRandomWeight(attributeNo);
				float error = 0;
				for(int j=0; j<myGuessList.size(); j++){
					ResultContainer thisResult = myGuessList.get(j);
					error += getErrorDiff(getScore(thisResult.result, thisPredict), thisResult.error);
				}
				resultList.add(new ResultContainer(thisPredict, error));
			}
		
			Collections.sort(resultList);
			resultList = resultList.subList(0, 200);
		
			for(int i=0; i<1000; i++){
				float[] thisPredict = getMutation(resultList.get(r.nextInt(5)).result);
				float error = 0;
				for(int j=0; j<myGuessList.size(); j++){
					ResultContainer thisResult = myGuessList.get(j);
					error += getErrorDiff(getScore(thisResult.result, thisPredict), thisResult.error);
				}
				resultList.add(new ResultContainer(thisPredict, error));
			}
			Collections.sort(resultList);
			resultList = resultList.subList(0, 200);
		}
		
		Collections.sort(resultList);
		
		float[] voting = new float[attributeNo];
		for(int i=0; i<1; i++){
			float[] thisResult = resultList.get(i).result;
			for(int j=0; j<attributeNo; j++){
				if(thisResult[j] > 0){
					voting[j]++;
				}else if(thisResult[j] < 0){
					voting[j]--;
				}
			}
		}
		
		for(int i=0; i<attributeNo; i++){
			if(voting[i] > 0){
				voting[i] = 1;
			}else{
				voting[i] = 0;
			}
		}
		StrategyService.printArray(voting);
		myPevGuess = voting;
		return voting;
	}
	
	
	private float[] getMutation(float[] input){
		int idx1 = r.nextInt(attributeNo);
		float value1 = input[idx1];
		
		for(int i=0; i<20; i++){
			int idx2 = r.nextInt(attributeNo);
			if(idx1 != idx2 && input[idx1]*input[idx2] < 0){
				input[idx1] = input[idx2];
				input[idx2] = value1;
				break;
			}
		}
		return input;
	}
	
	
	private float getErrorDiff(float actual, float expect){
		return Math.abs(actual - expect);
	}
	
	private float getScore(float[] candidate, float[] weight){
		int score = 0;
		for(int i=0; i<candidate.length; i++){
			score += candidate[i]*weight[i];
		}
		return score;
	}
	
	class ResultContainer implements Comparable<ResultContainer> {
		public float error;
		public float[] result;
		public float score;
		
		public ResultContainer(float[] result, float error){
			this.error = error;
			this.result = result;
		}
		@Override
		public int compareTo(ResultContainer other) {
			return this.error > other.error ? 1 : -1;
		}
		
	}

}
