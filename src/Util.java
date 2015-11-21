import strategies.matchmaker.MatchmakerStrategy;
import strategies.player.PlayerStrategy;
import game.PropertyService;


public class Util {
	
	final static String host = "localhost";
	final static String propertyFile = "strategy.properties";
	static PropertyService service = new PropertyService(propertyFile);
	
	final static int matchmakerPort = 9696;
	final static String matchmakerPropName = "MatchmakerStrategy";
	
	final static int playerPort = 6969;
	final static String playerPropName = "PlayerStrategy";
	
	public static String convertArrayToString(float[] input){
		StringBuilder sb = new StringBuilder();
		for(float f: input){
			sb.append(String.format("%1$,.2f", f));
			sb.append(" ");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}

	public static PlayerStrategy getPlayerStrategyImpl(int attributeNo){
		PlayerStrategy ps = PlayerStrategy.getStrategyImpl(service.getPropValues(playerPropName));
		ps.setAttributeNo(attributeNo);
		return ps;
	}
	
	public static MatchmakerStrategy getMatchmakerStrategyImpl(int attributeNo){
		MatchmakerStrategy ms = MatchmakerStrategy.getStrategyImpl(service.getPropValues(matchmakerPropName));
		ms.setAttributeNo(attributeNo);
		return ms;
	}
}
