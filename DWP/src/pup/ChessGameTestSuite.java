package pup;

import java.util.HashMap;
import java.util.Map;




public class ChessGameTestSuite {

	public static void main(String[] args) {

		Map<String, String[]> request = new HashMap<String, String[]>();
		
		/*	Test from a fresh board	*/
		//String[] begin = new String[]{"true"};
		//request.put("begin",begin);

		/* Test from a test board	*/
		
		
		String[] turn = new String[]{"0"};
		String[] board = getBoard("test1");
		String[] move = new String[]{"[x6_y2,x4_y2]"};
		request.put("whoToGo",turn);
		request.put("board",board);
		request.put("move",move);
		
		GameInterface game = new ChessGame(request, null);
		
		//game.makeMove(move[0]);
		String gs = game.getGameState();
		
		printBoard(gs);
		
		// Movement tests
		System.out.printf("\nBG000\tUSING TEST BOARD: 1\tROOK MOVEMENT\n\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Rook direction North \t\tIS VALID\n", testResults(game.isValid("[x3_y4,x0_y4]")));
		System.out.printf("Test02 result: %5s -\tWHITE Rook direction East \t\tIS VALID\n", testResults(game.isValid("[x3_y4,x3_y7]")));
		System.out.printf("Test03 result: %5s -\tWHITE Rook take opposition \t\tIS VALID\n", testResults(game.isValid("[x3_y4,x3_y2]")));
		System.out.printf("Test04 result: %5s -\tWHITE Rook take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x3_y4,x6_y4]")));
		System.out.printf("Test05 result: %5s -\tWHITE Rook direction South \t\tIS VALID\n", testResults(game.isValid("[x3_y4,x5_y4]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);
		System.out.printf("Test06 result: %5s -\tBLACK Rook full board South \t\tIS VALID\n", testResults(game.isValid("[x0_y7,x7_y7]")));
		System.out.printf("Test07 result: %5s -\tBLACK Rook direction North West \tIS NOT VALID\n", testResults(!game.isValid("[x3_y2,x1_y0]")));
		System.out.printf("Test08 result: %5s -\tBLACK Rook direction West \t\tIS VALID\n", testResults(game.isValid("[x3_y2,x3_y0]")));
		System.out.printf("Test09 result: %5s -\tBLACK Rook jump piece \t\t\tIS NOT VALID\n", testResults(!game.isValid("[x3_y2,x3_y6]")));
		System.out.printf("Test10 result: %5s -\tBLACK Rook take opposition \t\tIS VALID\n", testResults(game.isValid("[x3_y2,x3_y4]")));
		turn[0] = "0";
		request.put("whoToGo", turn);
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();

		
		System.out.printf("\nBG001\tUSING TEST BOARD: 1\tCOLOUR CHECK\n\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Pawn moves whoToGo = white \tIS VALID\n", testResults(game.isValid("[x6_y4,x4_y4]")));
		System.out.printf("Test02 result: %5s -\tBLACK Pawn moves whoToGo = white \tIS NOT VALID\n", testResults(!game.isValid("[x1_y5,x2_y5]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);	
		System.out.printf("Test03 result: %5s -\tBLACK Pawn moves whoToGo = black \tIS VALID\n", testResults(game.isValid("[x1_y5,x2_y5]")));
		System.out.printf("Test04 result: %5s -\tWHITE Pawn moves whoToGo = black \tIS NOT VALID\n", testResults(!game.isValid("[x6_y4,x4_y4]")));
		turn[0] = "0";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();

		
		System.out.printf("\nBG002\tUSING TEST BOARD: 1\tBISHOP MOVEMENT\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Bishop direction North \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y1,x3_y1]")));
		System.out.printf("Test02 result: %5s -\tWHITE Bishop direction East \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y1,x5_y7]")));
		System.out.printf("Test03 result: %5s -\tWHITE Bishop direction North East \tIS VALID\n", testResults(game.isValid("[x5_y1,x2_y4]")));
		System.out.printf("Test04 result: %5s -\tWHITE Bishop take opposition \t\tIS VALID\n", testResults(game.isValid("[x5_y1,x1_y5]")));
		System.out.printf("Test05 result: %5s -\tWHITE Bishop jump piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y1,x0_y6]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);		
		System.out.printf("Test06 result: %5s -\tBLACK Bishop take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x4_y6,x1_y3]")));
		System.out.printf("Test07 result: %5s -\tBLACK Bishop jump piece  \t\tIS NOT VALID\n", testResults(!game.isValid("[x4_y6,x0_y2]")));
		System.out.printf("Test08 result: %5s -\tBLACK Bishop take opposition \t\tIS VALID\n", testResults(game.isValid("[x4_y6,x6_y4]")));
		System.out.printf("Test09 result: %5s -\tBLACK Bishop direction North West \tIS VALID\n", testResults(game.isValid("[x4_y6,x2_y4]")));
		System.out.printf("Test10 result: %5s -\tBLACK Bishop direction South West \tIS VALID\n", testResults(game.isValid("[x4_y6,x5_y5]")));
		turn[0] = "0";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();

		
		System.out.printf("\nBG003\tUSING TEST BOARD: 1\tQUEEN MOVEMENT\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Queen direction North \t\tIS VALID\n", testResults(game.isValid("[x6_y3,x2_y3]")));
		System.out.printf("Test02 result: %5s -\tWHITE Queen take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x6_y3,x6_y4]")));
		System.out.printf("Test03 result: %5s -\tWHITE Queen direction North West \tIS VALID\n", testResults(game.isValid("[x6_y3,x3_y0]")));
		System.out.printf("Test04 result: %5s -\tWHITE Queen take opposition \t\tIS VALID\n", testResults(game.isValid("[x6_y3,x1_y3]")));
		System.out.printf("Test05 result: %5s -\tWHITE Queen jump piece \t\t\tIS NOT VALID\n", testResults(!game.isValid("[x6_y3,x4_y5]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);
		System.out.printf("Test06 result: %5s -\tBLACK Queen take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x1_y5]")));
		System.out.printf("Test07 result: %5s -\tBLACK Queen jump piece  \t\tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x7_y3]")));
		System.out.printf("Test08 result: %5s -\tBLACK Queen take opposition \t\tIS VALID\n", testResults(game.isValid("[x1_y3,x6_y3]")));
		System.out.printf("Test09 result: %5s -\tBLACK Queen direction South West \tIS VALID\n", testResults(game.isValid("[x1_y3,x4_y0]")));
		System.out.printf("Test10 result: %5s -\tBLACK Queen mimic Knight move \t\tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x2_y1]")));
		turn[0] = "0";
		request.put("board", getBoard("test2"));
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printBoard(gs);

		
		System.out.printf("\nBG004\tUSING TEST BOARD: 2\tKNIGHT MOVEMENT\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Knight direction North \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y4,x0_y4]")));
		System.out.printf("Test02 result: %5s -\tWHITE Knight direction East \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y4,x5_y7]")));
		System.out.printf("Test03 result: %5s -\tWHITE Knight jump piece \t\tIS VALID\n", testResults(game.isValid("[x7_y1,x6_y3]")));
		System.out.printf("Test04 result: %5s -\tWHITE Knight take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x7_y1,x5_y2]")));
		System.out.printf("Test05 result: %5s -\tWHITE Knight take opposition \t\tIS VALID\n", testResults(game.isValid("[x7_y1,x5_y0]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);
		System.out.printf("Test06 result: %5s -\tBLACK Knight move to empty space \tIS VALID\n", testResults(game.isValid("[x0_y1,x2_y0]")));
		System.out.printf("Test07 result: %5s -\tBLACK Knight move to empty space  \tIS VALID\n", testResults(game.isValid("[x0_y1,x2_y2]")));
		System.out.printf("Test08 result: %5s -\tBLACK Knight take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x0_y1,x1_y3]")));
		System.out.printf("Test09 result: %5s -\tBLACK Knight move across board \t\tIS NOT VALID\n", testResults(!game.isValid("[x0_y1,x7_y7]")));
		System.out.printf("Test10 result: %5s -\tBLACK Knight direction South East \tIS NOT VALID\n", testResults(!game.isValid("[x0_y1,x4_y5]")));
		turn[0] = "0";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		
		System.out.printf("\nBG005\tUSING TEST BOARD: 2\tPAWN MOVEMENT\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE Pawn one space forward \t\tIS VALID\n", testResults(game.isValid("[x5_y2,x4_y2]")));
		System.out.printf("Test02 result: %5s -\tWHITE Pawn forward two from start \tIS VALID\n", testResults(game.isValid("[x6_y1,x4_y1]")));
		System.out.printf("Test03 result: %5s -\tWHITE Pawn jump piece \t\t\tIS NOT VALID\n", testResults(!game.isValid("[x6_y4,x4_y4]")));
		System.out.printf("Test04 result: %5s -\tWHITE Pawn take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x6_y1,x5_y2]")));
		System.out.printf("Test05 result: %5s -\tWHITE Pawn take opposition \t\tIS VALID\n", testResults(game.isValid("[x6_y1,x5_y0]")));
		System.out.printf("Test06 result: %5s -\tWHITE Pawn forward two after start \tIS NOT VALID\n", testResults(!game.isValid("[x5_y2,x3_y2]")));
		System.out.printf("Test07 result: %5s -\tWHITE Pawn moves backwards \t\tIS NOT VALID\n", testResults(!game.isValid("[x5_y2,x6_y2]")));
		System.out.printf("Test08 result: %5s -\tWHITE Pawn takes opposition infront \tIS NOT VALID\n", testResults(!game.isValid("[x3_y5,x2_y5]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);
		System.out.printf("Test09 result: %5s -\tBLACK Pawn one space forward \t\tIS VALID\n", testResults(game.isValid("[x1_y3,x2_y3]")));
		System.out.printf("Test10 result: %5s -\tBLACK Pawn forward two from start \tIS VALID\n", testResults(game.isValid("[x1_y1,x3_y1]")));
		System.out.printf("Test11 result: %5s -\tBLACK Pawn jump piece \t\t\tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x3_y3]")));
		System.out.printf("Test12 result: %5s -\tBLACK Pawn take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x2_y4]")));
		System.out.printf("Test13 result: %5s -\tBLACK Pawn take opposition \t\tIS VALID\n", testResults(game.isValid("[x2_y4,x3_y5]")));
		System.out.printf("Test14 result: %5s -\tBLACK Pawn foward two after start \tIS NOT VALID\n", testResults(!game.isValid("[x2_y4,x4_y4]")));
		System.out.printf("Test15 result: %5s -\tBLACK Pawn moves backwards \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y4,x1_y4]")));
		System.out.printf("Test16 result: %5s -\tBLACK Pawn takes opposition infront \tIS NOT VALID\n", testResults(!game.isValid("[x1_y3,x2_y3]")));
		turn[0] = "0";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		
		System.out.printf("\nBG006\tUSING TEST BOARD: 2\tKING MOVEMENT\n");
		printWTG(gs);
		System.out.printf("Test01 result: %5s -\tWHITE King take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y3,x1_y2]")));
		System.out.printf("Test02 result: %5s -\tWHITE King move one space \t\tIS VALID\n", testResults(game.isValid("[x2_y3,x3_y3]")));
		System.out.printf("Test03 result: %5s -\tWHITE King move two spaces \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y3,x4_y3]")));
		System.out.printf("Test04 result: %5s -\tWHITE King mimic Knight move \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y3,x0_y4]")));
		System.out.printf("Test05 result: %5s -\tWHITE King take opposition \t\tIS VALID\n", testResults(game.isValid("[x2_y3,x1_y3]")));
		turn[0] = "1";
		request.put("whoToGo", turn);
		game = new ChessGame(request, null);
		gs = game.getGameState();
		printWTG(gs);
		System.out.printf("Test06 result: %5s -\tBLACK King take own piece \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y5,x1_y2]")));
		System.out.printf("Test07 result: %5s -\tBLACK King move one space \t\tIS VALID\n", testResults(game.isValid("[x2_y5,x3_y4]")));
		System.out.printf("Test08 result: %5s -\tBLACK King move two spaces \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y5,x0_y5]")));
		System.out.printf("Test09 result: %5s -\tBLACK King mimic Knight move \t\tIS NOT VALID\n", testResults(!game.isValid("[x2_y5,x4_y6]")));
		System.out.printf("Test10 result: %5s -\tBLACK King take opposition \t\tIS VALID\n", testResults(game.isValid("[x2_y5,x3_y5]")));
	}

	private static void printBoard(String gs) {
		String board = getParam(gs, "board");
		String rows[] = board.split("\\],\\[");
		System.out.println("\nCurrent GameState:");
		for (String r : rows) {
			r = r.replaceAll("\\]|\\[", "");
			String piece[] = r.split(",");
			for(String p : piece){
				if(p.length()<2)
					p = " "+p;
				System.out.print(p+" ");
			}
			System.out.println();
		}
	}

	private static void printWTG(String gs) {
		String wtg = getParam(gs, "whoToGo");
		String turn = (wtg.matches("0"))? "white":"black";
		System.out.printf("\nWho To Go: %s\n", turn);

	}

	private static String getParam(String gs, String param) {
		String gameParam[] = gs.split(",\"");
		for (String s : gameParam) {
			s = s.replaceAll("\"|[}|{]", "");
			if (s.split(":")[0].matches(param))
				return s.split(":")[1];
		}
		return null;
	}
	
	private static String[] getBoard(String boardNum){
		String[] board = null;
		switch(boardNum){
		case ("test1"):
			board = new String[]{
				  "[[0,0,0,0,0,0,0,8],"  // 0,7 - B Rook
				 + "[0,0,0,11,0,7,0,0]," // 1,3 - B Queen, 1,5 - B Pawn
				 + "[0,0,0,0,0,0,0,0],"
				 + "[0,0,8,0,2,0,0,0],"  // 3,2 - B Rook,  3,4 - W Rook
				 + "[0,0,0,0,0,0,10,0]," // 4,6 - B Bishop
				 + "[0,4,0,0,0,0,0,0],"  // 5,1 - W Bishop
				 + "[0,0,0,5,1,0,0,0],"  // 6,3 - W Queen, 6,4 - W Pawn 
				 + "[0,0,0,0,0,0,0,0]]"};
			break;
			
		case("test2"):
			board = new String[]{
				  "[[0,9,0,0,0,0,0,0],"   // 0,1 - B Knight
				 + "[0,7,1,7,0,0,0,0],"   // 1,1 - B Pawn, 1,2 - W Pawn, 1,3 - B Pawn
				 + "[0,0,0,6,7,12,0,0],"  // 2,3 - W King, 2,4 - B Pawn, 2,5 - B King
				 + "[0,0,0,0,0,1,0,0],"   // 3,5 - W Pawn
				 + "[0,0,0,0,0,0,0,0],"
				 + "[7,0,1,0,3,0,0,0],"   // 5,0 - B Pawn, 5,2 - W Pawn, 5,4 - W Knight
				 + "[0,1,0,0,1,0,0,0],"   // 6,1 - W Pawn, 6,4 - W Pawn
				 + "[0,3,0,0,0,0,0,0]]"}; // 7,1 - W Knight
			break;
		default:
			board = new String[]{
				  "[[8,9,10,11,0,10,9,8],"
				 + "[7,7,7,7,7,7,7,7],"
				 + "[0,0,0,0,0,0,0,0],"
				 + "[0,0,0,0,0,0,0,0],"
				 + "[0,0,0,0,0,0,0,0],"
				 + "[0,0,0,0,0,0,0,0],"
				 + "[1,1,1,1,1,1,1,1],"
				 + "[2,3,4,5,6,4,3,2]]"};
			break;
		}
		return board;
	}

	private static String testResults(boolean result){
		return (result)? "  PASSED  " : "!!FAILED!!";
	}
}


