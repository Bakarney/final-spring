package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;

/**
 * @author Naberezhniy Artur
 * 
 * Taking request parameter "id", and changes status of order with this id
 * to "rejected".
 */
@Component
public class OrderReject implements Command {
	
	private OrderDAO orderDAO;

	@Autowired
	public OrderReject(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	int id = Integer.valueOf(request.getParameter("id"));
    	orderDAO.setState(id, "rejected");
    	
    	response.sendRedirect("/final-spring/admin_orders");
	}

}
