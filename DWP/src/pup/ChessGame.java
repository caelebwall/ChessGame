package pup;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/////////bawbag
public class ChessGame implements GameInterface {

	private int board[][];
	private int whoToGo;

	public ChessGame(Map<String, String[]> requestParams, Object request) {
		if (requestParams.get("begin") != null) {
			createGame();
		} else {
			board = stringToBoard(requestParams.get("board")[0]);
			whoToGo = Integer.parseInt(requestParams.get("whoToGo")[0]);
		}
	}
	
	@Override
	public void makeMove(Object mv) {
		HashMap<String, Integer> moves = getMoves(mv);
		board[moves.get("toX")][moves.get("toY")] = board[moves.get("fromX")][moves.get("fromY")];
		board[moves.get("fromX")][moves.get("fromY")] = 0;
		whoToGo = (whoToGo == 0)? 1:0;	
	}

	@Override
	public String getGameState() {
		HashMap<String, String> gameState = new HashMap<String, String>();
		gameState.put("whoToGo", Integer.toString(whoToGo));
		gameState.put("board", boardToString(board));
		return toJSON(gameState);
	}

	@Override
	public boolean isValid(Object mv) {
		HashMap<String, Integer> moves = getMoves(mv);
		int pieceFrom = board[moves.get("fromX")][moves.get("fromY")];
		int pieceTo = board[moves.get("toX")][moves.get("toY")];
		System.out.println("pieceFrom:" + pieceFrom);
		System.out.println("WhoToGo:" + whoToGo);
		//Reject if it is not your go
		if ((pieceFrom>=7) != (whoToGo==1))
			return false;
		//Reject if a piece has been taken and it is the same color
		if (pieceTo!=0 && ((pieceFrom>=7)==(pieceTo>=7)))
			return false;
		return true;
	}
	
	// Converts request board into int[][]
	private int[][] stringToBoard(String input) {
		int[][] board = new int[8][8];
		String[] row = input.split("],");
		for (int i = 0; i < 8; i++) {
			row[i] = row[i].replaceAll("([^.]*\\[)|(\\][^.]*)", "");
			String temp[] = row[i].split(",");
			for (int j = 0; j < 8; j++)
				board[i][j] = Integer.parseInt(temp[j]);
		}
		return board;
	}

	// Convert 2D board array to string
	private String boardToString(int[][] board) {
		String boardString = "[";
		for (int[] row : board) {
			boardString += "[";
			for (int i : row)
				boardString += i + ",";
			boardString = boardString.substring(0, boardString.length() - 1)
					+ "],";
		}
		return boardString.substring(0, boardString.length() - 1) + "]";
	}

	// Convert HashMap into JSON formatted String
	private String toJSON(HashMap<String, String> gameState) {
		String JSONFormat = "{";
		for (Entry<String, String> gs : gameState.entrySet())
			JSONFormat += "\"" + gs.getKey() + "\":\"" + gs.getValue() + "\",";
		return JSONFormat.substring(0, JSONFormat.length() - 1) + "}";
	}
	
	// Split input move into usable HashMap
	// Move comes in as string of array split into to and from move parameters
	// Strip anything that is not numerical and convert into integer
	private HashMap<String, Integer> getMoves(Object mv){
		String[] move = mv.toString().split(",");
		String start = move[0], stop = move[1];
		HashMap<String, Integer> moves = new HashMap<String, Integer>();
		moves.put("fromX", Integer.parseInt(start.split("_")[0].replaceAll("\\D+","")));
		moves.put("fromY", Integer.parseInt(start.split("_")[1].replaceAll("\\D+","")));
		moves.put("toX", Integer.parseInt(stop.split("_")[0].replaceAll("\\D+","")));
		moves.put("toY", Integer.parseInt(stop.split("_")[1].replaceAll("\\D+","")));
		
		return moves;
	}
	
	// Create standard new chess game
	private void createGame() {
		whoToGo = 0; // White always moves first
		board = new int[][] { 
				{ 8, 9, 10, 11, 12, 10, 9, 8 },
				{ 7, 7, 7, 7, 7, 7, 7, 7 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 2, 3, 4, 5, 6, 4, 3, 2 } };
	}
	
	// Will check if the move is obstructed in a north or south direction
	private boolean northSouth(int fromX, int toX, int y){
		int dir = fromX - toX;
		//check south
		if(dir<0){
			for(int x=fromX+1; x<toX; x++)
				if(board[x][y] != 0)
					return false;
			return true;
		//check north
		}else{
			for(int j=fromX-1; j>toX; j--)
				if(board[j][y] != 0)
					return false;
			return true;
		}
	}

	// Will check if the move is obstructed in a east or west direction
	private boolean eastWest(int fromY, int toY, int x){
		int dir = fromY - toY;
		//check east
		if(dir<0){
			for(int i=fromY+1; i<toY; i++)
				if(board[x][i] != 0)
					return false;
			return true;
		//check west
		}else{
			for(int j=fromY-1; j>toY; j--)
				if(board[x][j] != 0)
					return false;
			return true;
		}
	}
}
