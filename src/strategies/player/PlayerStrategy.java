package strategies.player;

public abstract class PlayerStrategy {
	private static final String path = "strategies.player.";
	int attributeNo = 0;
	public abstract float[] getInitialWeights();
	public abstract float[] getNextWeights();
	public static PlayerStrategy getStrategyImpl(String className){
		try {
			PlayerStrategy impl = (PlayerStrategy)Class.forName(path+className).newInstance();
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
