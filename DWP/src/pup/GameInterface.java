package pup;

public interface GameInterface {
	
	void makeMove(Object mv);
	String getGameState();
	boolean isValid(Object mv);

}
