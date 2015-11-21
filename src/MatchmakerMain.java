import game.TCPClient;
import java.io.IOException;
import strategies.matchmaker.MatchmakerStrategy;

public class MatchmakerMain {
	
	TCPClient tcpClient;
	MatchmakerStrategy matchmakerStrategy;
	int attributeNo = 0;
	boolean firstGuess = true;
	int firstGuessSampleSize = 20;
	
	public static void main(String[] args) throws Exception{
		MatchmakerMain m = new MatchmakerMain();
		try{
			m.attributeNo = Integer.parseInt(args[0]);
		}catch(Exception e){
			System.out.println("Attribute number is wrong!!");
		}
		
		m.tcpClient = new TCPClient();
		m.tcpClient.startTCP(Util.host, Util.matchmakerPort);
		m.matchmakerStrategy = Util.getMatchmakerStrategyImpl(m.attributeNo);
		m.run();
		
		m.tcpClient.closeTCP();
	}

	private void run() throws IOException{
		boolean playing = true;
		
		while(playing){
			String input = tcpClient.read();
			System.out.println("Receive Input:" + input);

			if(firstGuess){
				firstGuess = false;
				String[] allInput = new String[firstGuessSampleSize];
				allInput[0] = input;
				for(int i=1; i<firstGuessSampleSize; i++){
					allInput[i] = tcpClient.read();
					//System.out.println(allInput[i]);
				}
				tcpClient.write(getFirstGuess(allInput));
			}else if(!"gameover".equals(input)){
				tcpClient.write(getNextGuess(input));
			}else{
				playing = false;
			}
		}
	}
	
	
	private String getFirstGuess(String[] input){
		float[][] attributes = new float[firstGuessSampleSize][attributeNo];
		float[] scores = new float[firstGuessSampleSize];
		
		for(int i=0; i<input.length; i++){
			String[] numbers = input[i].split(" ");
			for(int j=0; j<attributeNo; j++){
				attributes[i][j] = Float.parseFloat(numbers[j]);
			}
			scores[i] = Float.parseFloat(numbers[attributeNo+1]);
		}
		
		String result = Util.convertArrayToString(matchmakerStrategy.getFirstGuess(attributes, scores));	
		System.out.println("getFirstGuess=" + result);
		return result;
	}
	
	private String getNextGuess(String input){
		String[] inputList = input.split(" ");
		float score = Float.parseFloat(inputList[attributeNo+1]);
		String result = Util.convertArrayToString(matchmakerStrategy.getNextGuess(score));
		System.out.println("getNextGuess=" + result);
		return result;	
	}
	
}
