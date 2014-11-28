package connect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connect.ChessGameConnect;
import connect.GameInterfaceConnect;

/**
 * Servlet implementation class ChessGameConnect
 */
@WebServlet("/GameConnectServlet")
public class GameConnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameConnectServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String game = request.getParameter("game");
		String play = request.getParameter("play");

		response.setContentType("application/json");
		PrintWriter outg = response.getWriter();
		String uuid = UUID.randomUUID().toString().substring(0, 5);
		String filepath = getServletContext().getRealPath("/");
		File path = new File(filepath + "games");

		// If no games exist create a new game
		if (!path.exists()) {
			path.mkdir();
			path.setReadable(true, false);
			path.setWritable(true, false);
			newGame(uuid);
			outg.write(createGame(uuid));
			//Check current games
		} else {
			// game and turn required
			if (game!= null && play!=null) {
				String filePath = path.toString()+"/";
				String newGame = filePath + game +".json";
				File f = new File(newGame);
				//game exists return game
				if (f.exists()) {
					outg.write(readStatus(game));
					// File not found start fresh game
				} else {
					outg.write("{\"error\":\"file not found\"}");
				}
				// input variables not valid start fresh game
			} else {
				newGame(uuid);
				outg.write(createGame(uuid));
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter outp = response.getWriter();

		String gameID = request.getParameter("game");
		String play = request.getParameter("play");
		// is the file locked for the correct colour
		if(play.matches(isLocked(gameID))){
			// Create game
			GameInterfaceConnect game = new ChessGameConnect(request.getParameterMap(), null);
			String gs = game.getGameState();
			if (request.getParameter("move") != null && game.isValid(request.getParameter("move"))) {
				game.makeMove(request.getParameter("move"));
				gs = game.getGameState();
				gs = gs.substring(0, gs.length() - 1) + ",\"game\":\""+ gameID + "\",\"play\":\""+ play  +"\",\"valid\":\"true\"}";
				// update game file
				updateGame(gameID, gs);
				unlock(gameID);
			}else{
				gs = gs.substring(0, gs.length() - 1) + ",\"game\":\""+ gameID + "\",\"play\":\""+ play  +"\",\"valid\":\"false\"}";
				// update game file
				updateGame(gameID, gs);
			}
			outp.write(gs);
		}else{
			int t = 0, count = 60*60;
			while(!play.matches(isLocked(gameID)) && t < count)
				try {
					Thread.sleep(1000);
					t++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if(t >= count)
				outp.write("{\"timeout\":true}");
			else
				outp.write(readStatus(gameID));
		}
	}

	//returns colour of current lock
	private String isLocked(String gameID) {
		String filepath = getServletContext().getRealPath("/");
		File path = new File(filepath + "games");
		File white = new File(path.toString() + "/" + gameID + "W.lck");
		if(white.exists())
			return "white";
		return "black";
	}

	// Create NEW game file and lock to white
	private void newGame(String uuid) {
		String path = getServletContext().getRealPath("/games/");
		String name = "/" + uuid + ".json";
		File file = new File(path + name);
		file.setReadable(true, false);
		file.setWritable(true, false);
		FileWriter fstream;
		try {
			fstream = new FileWriter(file);
			BufferedWriter store = new BufferedWriter(fstream);
			store.write(createGame(uuid));
			store.newLine();
			store.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lockIt(uuid,"W");
	}

	// Update EXISTING game file and lock to opposite colour
	private void updateGame(String uuid, String gs) {
		String path = getServletContext().getRealPath("/games/");
		String name = "/" + uuid + ".json";
		File file = new File(path + name);
		FileWriter fstream;
		try {
			fstream = new FileWriter(file);
			BufferedWriter store = new BufferedWriter(fstream);
			store.write(gs);
			store.newLine();
			store.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Create Lock File
	private void lockIt(String uuid, String turn) {
		String path = getServletContext().getRealPath("/games/");
		String name = "/" + uuid + turn +".lck";
		File file = new File(path + name);
		FileWriter fstream;
		try {
			fstream = new FileWriter(file, true);
			BufferedWriter store = new BufferedWriter(fstream);
			store.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Swap colour of file lock
	private void unlock(String uuid){
		String filepath = getServletContext().getRealPath("/");
		File path = new File(filepath + "games");
		File white = new File(path.toString() + "/" + uuid + "W.lck");
		File black = new File(path.toString() + "/" + uuid + "B.lck");
		if(white.exists()){
			white.delete();
			lockIt(uuid, "B");
		}else if(black.exists()){
			black.delete();
			lockIt(uuid, "W");
		}
	}

	// Return file contents File
	private String readStatus(String game) throws FileNotFoundException{
		String path = getServletContext().getRealPath("/games/");
		String filepath = path + "/" + game + ".json";
		File file = new File(filepath);
		Scanner sc = new Scanner(file);
		String status = sc.nextLine();
		sc.close();
		return status;

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

	// Create new JSON string of new game parameters
	private String createGame(String game){
		String whoToGo = "0"; // White always moves first
		int [][] board = new int[][] { 
				{ 8, 9, 10, 11, 12, 10, 9, 8 },
				{ 7, 7, 7, 7, 7, 7, 7, 7 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 2, 3, 4, 5, 6, 4, 3, 2 } };
		HashMap<String,String> gs = new HashMap<String, String>();
		gs.put("game", game);
		gs.put("play", "white");
		gs.put("whoToGo", whoToGo);
		gs.put("board", boardToString(board));
		return toJSON(gs);
	}
}