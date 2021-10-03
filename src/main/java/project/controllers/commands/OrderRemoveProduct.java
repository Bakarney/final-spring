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
public class OrderRemoveProduct implements Command {
	
	private OrderDAO orderDAO;

	@Autowired
	public OrderRemoveProduct(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Integer id = Integer.valueOf(request.getParameter("id"));
		HttpSession session = request.getSession();
		Order order = (Order)session.getAttribute("order");
		order.getCart().remove(id);
		
		if (order.getCart().isEmpty()) {
			session.removeAttribute("order");
			if (request.getUserPrincipal() != null)
				orderDAO.delete(order.getId());
		} else {
			session.setAttribute("order", order);
			if (request.getUserPrincipal() != null)
				orderDAO.removeProduct(order.getId(), id);
		}
		
		response.sendRedirect("/final-spring/cart");
	}

}
