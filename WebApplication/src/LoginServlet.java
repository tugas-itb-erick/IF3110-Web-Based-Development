

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String pass = request.getParameter("pass");
		String body = "{\"username\": \"" + username + "\",\"pass\": \"" + pass + "\"}";
		
		URL url = new URL ("http://localhost:7000/IdentityService/Login");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
		connection.setRequestProperty("Content-Length", "" +  Integer.toString(body.getBytes().length));
	    
		DataOutputStream out = new DataOutputStream(connection.getOutputStream ());
	    out.writeBytes(body);
	    out.flush();
	    out.close();
		
	    connection.connect();
		
	    String result;
	    BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    int result2 = bis.read();
	    while(result2 != -1) {
	        buf.write((byte) result2);
	        result2 = bis.read();
	    }
	    result = buf.toString();
	    JSONObject resultJSON = null;
	    String status = "";
	    try {
			resultJSON = new JSONObject(result);
			status = resultJSON.getString("status");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    response.setContentType("text/html");
	    PrintWriter output = response.getWriter();
	    
	    if(status.equals("ok")) {
	    	try {
				String token = resultJSON.getString("token");
				output.print("<p>" + token + "</p>");
				String expiryTime = resultJSON.getString("expiry");
				output.print("<p>" + expiryTime + "</p>");
				Cookie cookieToken = new Cookie("token", token);
				Cookie cookieExpiry = new Cookie("expiry", expiryTime);
				response.addCookie(cookieToken);
				response.addCookie(cookieExpiry);
	    	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	    else {
	    	
	    }
	    
	    // response.sendRedirect("Login.jsp");
		
	}

}
