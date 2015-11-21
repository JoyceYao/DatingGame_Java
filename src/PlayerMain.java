import game.TCPClient;
import java.io.IOException;
import strategies.player.PlayerStrategy;

public class PlayerMain {
	
	TCPClient tcpClient;
	PlayerStrategy playerStrategy;

	int attributeNo = 0;
	boolean firstWeight = true;
	
	public static void main(String[] args) throws Exception{
		PlayerMain m = new PlayerMain();
		try{
			m.attributeNo = Integer.parseInt(args[0]);
		}catch(Exception e){
			System.out.println("Attribute number is wrong!!");
		}
		
		m.tcpClient = new TCPClient();
		m.tcpClient.startTCP(Util.host, Util.playerPort);
		m.playerStrategy = Util.getPlayerStrategyImpl(m.attributeNo);
		m.run();
		
		m.tcpClient.closeTCP();
	}

	private void run() throws IOException{
		boolean playing = true;
		tcpClient.write(getWeights());
		
		while(playing){
			String input = tcpClient.readAll();
			System.out.println("Receive Input:" + input);
		
			if(!"gameover".equals(input)){
				tcpClient.write(getWeights());
			}else{
				playing = false;
			}
		}
	}
	
	private String getWeights(){
		if(firstWeight){
			firstWeight = false;
			return Util.convertArrayToString(playerStrategy.getInitialWeights());
		}else{
			return Util.convertArrayToString(playerStrategy.getNextWeights());
		}
	}
	
}
