package project.controllers.commands;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Controller
public class BuildOrderConflict implements Command {
	
	private ProductDAO productDAO;

	@Autowired
	public BuildOrderConflict(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		HttpSession session = request.getSession();
		
		List<Product> products = new ArrayList<>();
		List<Integer> list = ((Order)session.getAttribute("order")).getCart();
		for (Integer i : list)
			products.add(productDAO.get(i));
		request.setAttribute("local_products", products);
		
		products = new ArrayList<>();
		list = ((Order)session.getAttribute("cloud_order")).getCart();
		for (Integer i : list)
			products.add(productDAO.get(i));
		request.setAttribute("cloud_products", products);
	}

}
