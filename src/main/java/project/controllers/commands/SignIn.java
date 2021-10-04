package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

/**
 * @author Naberezhniy Artur
 * @deprecated
 * 
 * Adding to session attribute user if it active and his email and password 
 * is correct. If user is admin redirects him to admin page.
 */
@Component
public class SignIn implements Command {
	
	private UserDAO userDAO;
	private OrderDAO orderDAO;
	
	@Autowired
	public SignIn(UserDAO userDAO, OrderDAO orderDAO) {
		this.userDAO = userDAO;
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = userDAO.get(email, password);
		HttpSession session = request.getSession();
		if (user.getName() == null) {
			response.sendRedirect("/final-spring/sign_in?error=wrong");
			return;
		}
		if (!userDAO.isActive(user.getId())) {
			response.sendRedirect("/final-spring/sign_in?error=blocked");
			return;
		}
		session.setAttribute("user", user);
		if (userDAO.isAdmin(user.getId())) {
			response.sendRedirect("/final-spring/admin_catalog");
		}
		else {
			Order sessionOrder = (Order)session.getAttribute("order");
			Order cloudOrder = orderDAO.get(user);
			if (sessionOrder != null && cloudOrder != null) {
				session.setAttribute("cloud_order", cloudOrder);
				response.sendRedirect("/final-spring/order_conflict");
			}
			else if (sessionOrder != null) {
				Order order = orderDAO.create(user.getId());
				for (Integer i : sessionOrder.getCart())
					orderDAO.addProduct(order.getId(), i);
				response.sendRedirect("/final-spring/profile");
			} else {
				session.setAttribute("order", cloudOrder);
				response.sendRedirect("/final-spring/profile");
			}
		}
	}
}
