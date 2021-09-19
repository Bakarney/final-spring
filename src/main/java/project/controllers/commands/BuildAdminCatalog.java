package project.controllers.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class BuildAdminCatalog implements Command {
	
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private ProducerDAO producerDAO;

	@Autowired
	public BuildAdminCatalog(ProductDAO productDAO, CategoryDAO categoryDAO, ProducerDAO producerDAO) {
		this.productDAO = productDAO;
		this.categoryDAO = categoryDAO;
		this.producerDAO = producerDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		String sort = request.getParameter("sort");
		String gender = request.getParameter("gender");
		String[] producersData = request.getParameterValues("producer");
		String[] categoriesData = request.getParameterValues("category");
		String bot = request.getParameter("bot");
		String top = request.getParameter("top");
		
		List<Product> products = productDAO.getFiltered(categoriesData, producersData, gender, bot, top, sort, null, null);
		request.setAttribute("products", products);
		
		List<String> categories = categoryDAO.getAll();
		request.setAttribute("categories", categories);
		
		List<String> producers = producerDAO.getAll();
		request.setAttribute("producers", producers);
	}

}
