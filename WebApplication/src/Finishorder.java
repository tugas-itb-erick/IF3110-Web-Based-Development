

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Finishorder
 */
@WebServlet("/Finishorder")
public class Finishorder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Finishorder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idc = request.getParameter("id_active");
		String idd = request.getParameter("id_driver");
		String star = request.getParameter("star");
		String feedback = request.getParameter("comment");
		String pickup = request.getParameter("pickup");
		String dest = request.getParameter("dest");
		
		com.services.History history = new com.services.History();
		history.setIdCustomer(Integer.parseInt(idc));
		history.setIdDriver(Integer.parseInt(idd));
		history.setRating(Integer.parseInt(star));
		history.setFeedback(feedback);
		history.setPickUp(pickup);
		history.setDestination(dest);
		
		com.services.HistoryServiceProxy proxy = new com.services.HistoryServiceProxy();
		proxy.createHistory(history);
		proxy.updateCustomer(Integer.parseInt(idd), history);
		
		response.sendRedirect("http://localhost:9000/WebApplication/Profile.jsp?id_active=" + idc);
	}
}