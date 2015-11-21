package strategies.matchmaker;

public abstract class MatchmakerStrategy {
	private static final String path = "strategies.matchmaker.";
	int attributeNo = 0;
	public abstract float[] getFirstGuess(float[][] attributes, float[] scores);
	public abstract float[] getNextGuess(float score);
	
	public static MatchmakerStrategy getStrategyImpl(String className){
		try {
			MatchmakerStrategy impl = (MatchmakerStrategy)Class.forName(path+className).newInstance();
			return impl;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setAttributeNo(int attributeNo){
		this.attributeNo = attributeNo;
	}
}
