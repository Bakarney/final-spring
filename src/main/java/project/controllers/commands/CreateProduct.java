package project.controllers.commands;

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
 * Taking request parameters "name", "category", "gender", "producer",
 * "number", "price", "photo" and creation new Product in DB.
 */
@Component
public class CreateProduct implements Command {
	
	private ProductDAO productDAO;

	@Autowired
	public CreateProduct(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
    	Product prod = new Product();
		prod.setName(request.getParameter("name"));
		prod.setCategory(request.getParameter("category"));
		prod.setGender(request.getParameter("gender"));
		prod.setProducer(request.getParameter("producer"));
		prod.setNumber(Integer.valueOf(request.getParameter("number")));
		prod.setPrice(Float.valueOf(request.getParameter("price")));
		prod.setPhoto((String)model.getAttribute("file"));
		productDAO.create(prod);
		
		response.sendRedirect("/final-spring/admin_catalog");
	}

}
