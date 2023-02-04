package servlet;

import DAO.FormDAO;
import validazione.ValidateFieldsAperturaForm;
import validazione.ValidateFieldsRegistration;

import com.mongodb.client.MongoClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class AperturaFormServlet
 */
@WebServlet("/AperturaFormServlet")
public class AperturaFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AperturaFormServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        String titolo = request.getParameter("titolo");
        String descrizione = request.getParameter("descrizione");
        
        
        ValidateFieldsAperturaForm validate = new ValidateFieldsAperturaForm();
        
        //TITOLO VALIDATE
        
       if(titolo.length()< 2) {
    	      request.setAttribute("error_message", "Il campo Titolo deve contenere almeno 2 caratteri");
              request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       }
       
       if(titolo.length() > 255) {
 	      request.setAttribute("error_message", "Il campo Titolo deve contenere al massimo 255 caratteri");
          request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       }
       
       if(!validate.validateTitolo(titolo)) {
  	      request.setAttribute("error_message", "Il campo Titolo non rispetta il formato stabilito");
          request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       }
       
       
       //DESCRIZIONE VALIDATE
       
       	if(descrizione.length() < 2) {
       		request.setAttribute("error_message", "Il campo Descrizione deve contenere almeno 2 caratteri");
            request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       	}
       	
       	if(descrizione.length() > 800) {
     		request.setAttribute("error_message", "Il campo Descrizione deve contenere al massimo 255 caratteri");
            request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       	}
       	
       	if(!validate.validateDescrizione(descrizione)) {
       		request.setAttribute("error_message", "Il campo Descrizione non rispetta il formato stabilito");
            request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
       	}
       	
       		
        if ( titolo == null || descrizione == null || 
                "".equals(titolo) || "".equals(descrizione)) {

            request.setAttribute("error_message", "I valori non possono essere nulli");

        } else {
            MongoClient mongoClient = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
            HttpSession session = request.getSession();

            String email = (String) session.getAttribute("email");
            FormDAO formDAO = new FormDAO(mongoClient);
            boolean isUserFound = formDAO.CreateNewForm(titolo, descrizione, email);

            if (isUserFound) {
                request.getRequestDispatcher("/MyForm.jsp").forward(request, response);
            } else {
                request.setAttribute("error_message", "Creazione del form non avvenuta con successo");
                request.getRequestDispatcher("/AperturaForm.jsp").forward(request, response);
            }
        }
    }
}
