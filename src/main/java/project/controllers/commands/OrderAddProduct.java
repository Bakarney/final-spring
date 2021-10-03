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
public class OrderAddProduct implements Command {

	private OrderDAO orderDAO;
	private UserDAO userDAO;
	
	@Autowired
	public OrderAddProduct(OrderDAO orderDAO, UserDAO userDAO) {
		this.orderDAO = orderDAO;
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int id = Integer.valueOf(request.getParameter("product_id"));
		HttpSession session = request.getSession();
		User user = userDAO.get(request.getUserPrincipal().getName());
		Order order;
		
		if (user != null) {
			order = orderDAO.get(user);
			if (order == null) 
				order = orderDAO.create(user.getId());
			orderDAO.addProduct(order.getId(), id);
			order.getCart().add(id);
			session.setAttribute("order", order);
		}
		else {
			order = (Order)session.getAttribute("order");
			if (order == null)
				order = new Order();
			order.getCart().add(id);
			session.setAttribute("order", order);
		}
		
		response.sendRedirect("/final-spring/product?id=" + id);
	}
}
