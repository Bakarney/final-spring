package project.controllers.commands;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
 * 
 * Making List of products of order from session or from DB, if session
 * contains parameter "user". If both of them isn't in session will return
 * empty List.
 */
@Component
public class BuildCart implements Command {
	
	private OrderDAO orderDAO;
	private ProductDAO productDAO;
	private UserDAO userDAO;

	@Autowired
	public BuildCart(OrderDAO orderDAO, ProductDAO productDAO, UserDAO userDAO) {
		this.orderDAO = orderDAO;
		this.productDAO = productDAO;
		this.userDAO = userDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		Principal principal = request.getUserPrincipal();
		User user;
		if (principal == null)
			user = null;
		else
			user = userDAO.get(principal.getName());
		Order order;
		List<Product> products = new ArrayList<>();
		
		if (user != null) {
			order = orderDAO.get(user);
		}
		else 
			order = (Order)session.getAttribute("order");
		
		if (order != null)
			for (int i : order.getCart())
				products.add(productDAO.get(i));
		
		model.addAttribute("products", products);
	}
}
