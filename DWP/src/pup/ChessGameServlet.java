package pup;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChessGameServlet
 */
@WebServlet("/ChessGameServlet")
public class ChessGameServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChessGameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Set response content type
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        // Create new game
		GameInterface game = new ChessGame(request.getParameterMap(), null);
		String gs = game.getGameState();
		if(request.getParameter("move") != null && game.isValid(request.getParameter("move"))){
			game.makeMove(request.getParameter("move"));
			gs = game.getGameState();
			gs = gs.substring(0,gs.length()-1)+",\"valid\":true}";
		}
	
		out.write(gs);
      
	}
	
}
