package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;

/**
 * @author Naberezhniy Artur
 * 
 * Taking request parameter "id", and deletes user with this id from DB.
 */
@Component
public class DeleteUser implements Command {
	
	private UserDAO userDAO;

	@Autowired
	public DeleteUser(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		userDAO.delete(Integer.valueOf(request.getParameter("id")));
    	response.sendRedirect("/final-spring/admin_users");
	}

}
