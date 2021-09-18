package project.controllers.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class BuildCatalog implements Command {
	
	@Autowired
	public BuildCatalog(ProductDAO productDAO, CategoryDAO categoryDAO, ProducerDAO producerDAO) {
		this.productDAO = productDAO;
		this.categoryDAO = categoryDAO;
		this.producerDAO = producerDAO;
	}
	
	private ProductDAO productDAO;
	private CategoryDAO categoryDAO;
	private ProducerDAO producerDAO;
	
	private static final int ROW_LENGTH = 4;
	private static final int PAGE_LENGTH = 8;
	
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		
		String sort = request.getParameter("sort");
		String gender = request.getParameter("gender");
		String[] producersData = request.getParameterValues("producer");
		String[] categoriesData = request.getParameterValues("category");
		String bot = request.getParameter("bot");
		String top = request.getParameter("top");
		String page = request.getParameter("page");
		page = (page == null) ? "1" : page;
		String start = String.valueOf((Integer.valueOf(page) - 1) * PAGE_LENGTH);
		
		List<Product> products = productDAO.getFiltered(categoriesData, producersData, gender, bot, top, sort, start, String.valueOf(PAGE_LENGTH));
		int numberPages = (productDAO.count(categoriesData, producersData, gender, bot, top) - 1)/PAGE_LENGTH + 1;
		model.addAttribute("numberPages", numberPages);
		
		List<List<Product>> listProducts = new ArrayList<>();
		for (int i = 0; i < products.size(); i++) {
			if ((i+1)%ROW_LENGTH == 1) 
				listProducts.add(new ArrayList<>());
			listProducts.get(listProducts.size() - 1).add(products.get(i));
		}
		model.addAttribute("listProducts", listProducts);
		
		List<String> categories = categoryDAO.getAll();
		model.addAttribute("categories", categories);
		
		List<String> producers = producerDAO.getAll();
		model.addAttribute("producers", producers);
	}
}
