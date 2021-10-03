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
public class BuildProfile implements Command {
	
	private UserDAO userDAO;
	private OrderDAO orderDAO;

	@Autowired
	public BuildProfile(UserDAO userDAO, OrderDAO orderDAO) {
		this.userDAO = userDAO;
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		User user = userDAO.get(request.getUserPrincipal().getName());
		model.addAttribute("user", user);
		
		HttpSession session = request.getSession();
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
			session.setAttribute("order", order);
		} else {
			session.setAttribute("order", cloudOrder);
		}
	}
}
