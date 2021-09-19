package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class ManageOrderConflict implements Command {
	
	private OrderDAO orderDAO;

	public ManageOrderConflict(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String command = request.getParameter("command");
		HttpSession session = request.getSession();
		switch (command) {
		case "local":
			User user = (User)session.getAttribute("user");
			Order order = (Order)session.getAttribute("cloud_order");
			orderDAO.delete(order.getId());
			session.removeAttribute("cloud_order");
			order = (Order)session.getAttribute("order");
			Order newOrder = orderDAO.create(user.getId());
			for (Integer i : order.getCart()) 
				orderDAO.addProduct(newOrder.getId(), i);
			session.setAttribute("order", newOrder);
			break;
		case "cloud":
			session.removeAttribute("cloud_order");
			break;
		case "combine":
			Order cloudOrder = (Order)session.getAttribute("cloud_order");
			Order localOrder = (Order)session.getAttribute("order");
			
			session.removeAttribute("cloud_order");
			for (Integer i : localOrder.getCart()) {
				orderDAO.addProduct(cloudOrder.getId(), i);
				cloudOrder.getCart().add(i);
			}
			session.setAttribute("order", cloudOrder);
			break;
		}
		response.sendRedirect("/final-spring/profile");
	}

}
