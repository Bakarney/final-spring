package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;

/**
 * 
 * @author Naberezhniy Artur
 * 
 * Taking request parameter "id", and changes admin of user with this id.
 */
@Component
public class AdminUser implements Command {
	
	private UserDAO userDAO;

	@Autowired
	public AdminUser(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	int id = Integer.valueOf(request.getParameter("id"));
    	userDAO.setAdmin(id, !userDAO.isAdmin(id));
    	response.sendRedirect("/final-spring/admin_users");
	}

}
