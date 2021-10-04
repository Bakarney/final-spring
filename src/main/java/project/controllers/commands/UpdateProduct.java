package project.controllers.commands;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

/**
 * @author Naberezhniy Artur
 * 
 * Taking request parameters "id", "name", "category", "gender", "producer",
 * "number", "price", "photo". Changes all information about product with 
 * taken id.
 */
@Component
public class UpdateProduct implements Command {
	
	private ProductDAO productDAO;

	@Autowired
	public UpdateProduct(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		Product prod = new Product();
		prod.setId(Integer.valueOf(request.getParameter("id")));
		prod.setName(request.getParameter("name"));
		prod.setCategory(request.getParameter("category"));
		prod.setGender(request.getParameter("gender"));
		prod.setProducer(request.getParameter("producer"));
		prod.setNumber(Integer.valueOf(request.getParameter("number")));
		prod.setPrice(Float.valueOf(request.getParameter("price")));
		prod.setPhoto((String)model.getAttribute("photo"));
		productDAO.update(prod);
		
		response.sendRedirect("/final-spring/admin_catalog");
	}
}
