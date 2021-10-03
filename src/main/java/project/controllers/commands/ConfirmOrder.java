package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class ConfirmOrder implements Command {
	
	private OrderDAO orderDAO;
	private UserDAO userDAO;

	@Autowired
	public ConfirmOrder(OrderDAO orderDAO, UserDAO userDAO) {
		this.orderDAO = orderDAO;
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int id = Integer.valueOf(request.getParameter("order_id"));
		HttpSession session = request.getSession();
		User user = userDAO.get(request.getUserPrincipal().getName());
		
		if (user == null) {
			response.sendRedirect("/final-spring/sign_in");
		} else {
			session.removeAttribute("order");
			orderDAO.setState(id, "registrated");
			response.sendRedirect("/final-spring/home");
		}
	}

}
