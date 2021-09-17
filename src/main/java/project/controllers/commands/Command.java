package project.controllers.commands;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;

import project.DAO.*;
import project.config.SpringConfig;
import project.entities.*;

public class Command {
	
	private static final int PAGE_LENGTH = 4;
	
	public static void execute(HttpServletRequest request, Model model) throws SQLException {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);

		ProductDAO productDao = context.getBean(ProductDAO.class);
		CategoryDAO categoryDao = context.getBean(CategoryDAO.class);
		ProducerDAO producerDao = context.getBean(ProducerDAO.class);
		
		String sort = request.getParameter("sort");
		String gender = request.getParameter("gender");
		String[] producersData = request.getParameterValues("producer");
		String[] categoriesData = request.getParameterValues("category");
		String bot = request.getParameter("bot");
		String top = request.getParameter("top");
		String page = request.getParameter("page");
		page = (page == null) ? "1" : page;
		String start = String.valueOf((Integer.valueOf(page) - 1) * PAGE_LENGTH);
		
		List<Product> products = productDao.getFiltered(categoriesData, producersData, gender, bot, top, sort, start, String.valueOf(PAGE_LENGTH));
		int numberPages = (productDao.count(categoriesData, producersData, gender, bot, top) - 1)/PAGE_LENGTH + 1;
		model.addAttribute("products", products);
		model.addAttribute("numberPages", numberPages);
		
		List<String> categories = categoryDao.getAll();
		model.addAttribute("categories", categories);
		
		List<String> producers = producerDao.getAll();
		model.addAttribute("producers", producers);
		
		context.close();
	}
}
