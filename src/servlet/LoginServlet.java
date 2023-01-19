package servlet;
import java.io.IOException;
import DAO.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    // This method is called by the servlet container to process a 'post' request
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        handleRequest(req, resp);
    }
 
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
 
        // Reading post parameters from the request
        String param1 = req.getParameter("email"), 
                param2 = req.getParameter("password");
        System.out.println(param1 + "," +param2);
 
        // Checking for null and empty values
        if(param1 == null || param2 == null || "".equals(param1) || "".equals(param2)) {
            req.setAttribute("error_message", "Please enter login id and password");
            req.getRequestDispatcher("/logout.jsp").forward(req, resp);
        } else {
            boolean isUserFound = Util.searchUserInDb(param1, param2);
            if(isUserFound) {               
                req.getRequestDispatcher("/profile.html").forward(req, resp);
            } else {
                req.setAttribute("error_message", "You are not an authorised user. Please check with administrator.");
                req.getRequestDispatcher("/logout.jsp").forward(req, resp);
            }   
        }       
    }
}