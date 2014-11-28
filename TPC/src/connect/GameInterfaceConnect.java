package connect;

public interface GameInterfaceConnect {
	
	void makeMove(Object mv);
	String getGameState();
	boolean isValid(Object mv);

}
