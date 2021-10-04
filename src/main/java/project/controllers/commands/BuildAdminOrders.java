package project.controllers.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

/**
 * @author Naberezhniy Artur
 * 
 * Making List of all Orders from DAO and puts it in request attribute "orders".
 * In orders list after each order goes empty orders containing only info
 * about one product of this order.
 * Also making Map of all user id to their names and all products id and
 * their names.
 */
@Component
public class BuildAdminOrders implements Command {
	
	private ProductDAO productDAO;
	private UserDAO userDAO;
	private OrderDAO orderDAO;

	@Autowired
	public BuildAdminOrders(ProductDAO productDAO, UserDAO userDAO, OrderDAO orderDAO) {
		this.productDAO = productDAO;
		this.userDAO = userDAO;
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Map<Integer, String> products = new HashMap<>();
		Map<Integer, String> users = new HashMap<>();
		List<Order> orders = orderDAO.getAll();
		List<Order> preparedOrders = new ArrayList<>();
		
		for (Order order : orders) {
			boolean firstIter = true;
			for (Integer i : order.getCart()) {
				users.put(order.getUserId(), userDAO.get(order.getUserId()).getName());
				products.put(i, productDAO.get(i).getName());
				Order tmpOrder = new Order();
				if (firstIter) {
					firstIter = false;
					tmpOrder.setId(order.getId());
					tmpOrder.setUserId(order.getUserId());
					tmpOrder.setState(order.getState());
					tmpOrder.setCart(order.getCart());
				} else {
					tmpOrder.setCart(new ArrayList<Integer>());
					tmpOrder.getCart().add(i);
				}
				preparedOrders.add(tmpOrder);
			}
		}
		
		model.addAttribute("orders", preparedOrders);
		model.addAttribute("products", products);
		model.addAttribute("users", users);
	}

}
