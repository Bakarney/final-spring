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
		session.setAttribute("order", order);
		
		orderDAO.removeProduct(order.getId(), id);
		
		if (orderDAO.get(order.getId()) == null)
			orderDAO.delete(order.getId());
		
		response.sendRedirect("/final-spring/cart");
	}

}
