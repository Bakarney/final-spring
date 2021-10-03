package project.controllers.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class BuildAdminUsers implements Command {
	
	private UserDAO userDAO;

	@Autowired
	public BuildAdminUsers(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		List<User> users = userDAO.getAll();
		model.addAttribute("users", users);
	}

}
