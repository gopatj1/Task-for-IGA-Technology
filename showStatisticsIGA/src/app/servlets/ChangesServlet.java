package app.servlets;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import app.entities.Users;
import app.entities.Changes;

@WebServlet(urlPatterns = {"/index", "/index.html", "/"})
public class ChangesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChangesServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //create checkedUsers by checked checkbox
		Changes.checkedUsers.clear(); //clear old checked users array
        for (Integer i = 0; i < Users.getSize(); i++) { //check all checkbox
        	if (req.getParameter(i.toString()) != null) //if checbox is checked
        		Changes.checkedUsers.add(Users.getUser(i)); //addCheckedUsers
        }

        //create date
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); //date format for parsing
        try {
			Changes.leftDate = ft.parse(req.getParameter("leftDate")).getTime(); //left date in ms
			Changes.rightDate = ft.parse(req.getParameter("rightDate")).getTime(); //right date in ms
		} catch (ParseException e) {
			e.printStackTrace();
		}

        Changes.changeType = req.getParameter("changeType"); //type
        Changes.stepX = Long.parseLong(req.getParameter("stepX")); //step of X-axis
        
        resp.sendRedirect("views/graphic.jsp"); //go to graphic
    }

}
