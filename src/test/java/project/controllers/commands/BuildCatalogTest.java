package project.controllers.commands;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.ui.Model;

import project.entities.*;
import project.config.SpringConfig;
import project.config.TestContext;
import project.DAO.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, SpringConfig.class})
@WebAppConfiguration
public class BuildCatalogTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private CategoryDAO categoryDAO;
    
    @Autowired
    private ProducerDAO producerDAO;
    
    @Autowired
    private Command buildCatalog;

	@Test
	public void fullBuildCatalogTest() throws Exception {
		String sort = "testSort";
		String gender = "testGender";
		String[] producer = new String[] {"testProducer"};
		String[] category = new String[] {"testCategory"};
		String bot = "1";
		String top = "13";
		String page = "5";
		List<Product> products = new ArrayList<>();
		List<String> categories = new ArrayList<>();
		List<String> producers = new ArrayList<>();
		
		Mockito.when(request.getParameter("sort")).thenReturn(sort);
		Mockito.when(request.getParameter("gender")).thenReturn(gender);
		Mockito.when(request.getParameterValues("producer")).thenReturn(producer);
		Mockito.when(request.getParameterValues("category")).thenReturn(category);
		Mockito.when(request.getParameter("bot")).thenReturn(bot);
		Mockito.when(request.getParameter("top")).thenReturn(top);
		Mockito.when(request.getParameter("page")).thenReturn(page);
		Mockito.when(productDAO.getFiltered(category, producer, gender, bot, top, sort, "20", "24")).thenReturn(products);
		Mockito.when(productDAO.count(category, producer, gender, bot, top)).thenReturn(7);
		Mockito.when(categoryDAO.getAll()).thenReturn(categories);
		Mockito.when(producerDAO.getAll()).thenReturn(producers);
		
		buildCatalog.execute(request, response, model);
		
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("listProducts", products);
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("categories", categories);
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("producers", producers);
	}
	
	@Test
	public void emptyBuildCatalogTest() throws Exception {
		int numberPages = 1;
		String sort = null;
		String gender = null;
		String[] producer = null;
		String[] category = null;
		String bot = null;
		String top = null;
		String page = null;
		List<Product> products = new ArrayList<>();
		List<String> categories = new ArrayList<>();
		List<String> producers = new ArrayList<>();
		
		Mockito.when(request.getParameter("sort")).thenReturn(sort);
		Mockito.when(request.getParameter("gender")).thenReturn(gender);
		Mockito.when(request.getParameterValues("producer")).thenReturn(producer);
		Mockito.when(request.getParameterValues("category")).thenReturn(category);
		Mockito.when(request.getParameter("bot")).thenReturn(bot);
		Mockito.when(request.getParameter("top")).thenReturn(top);
		Mockito.when(request.getParameter("page")).thenReturn(page);
		Mockito.when(productDAO.getFiltered(category, producer, gender, bot, top, sort, "20", "24")).thenReturn(products);
		Mockito.when(productDAO.count(category, producer, gender, bot, top)).thenReturn(numberPages);
		Mockito.when(categoryDAO.getAll()).thenReturn(categories);
		Mockito.when(producerDAO.getAll()).thenReturn(producers);
		
		buildCatalog.execute(request, response, model);
		
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("numberPages", numberPages);
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("listProducts", products);
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("categories", categories);
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute("producers", producers);
	}
}
