package project.controllers.commands;

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
public class BuildProductTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private Command buildProduct;

    @Test
    public void execute() throws Exception {
		int product_id = 1;
		
		Mockito.when(request.getParameter("id")).thenReturn(String.valueOf(product_id));
		
		Product prod = new Product();
		Mockito.when(productDAO.get(product_id)).thenReturn(prod);
    	
		buildProduct.execute(request, response, model);
		
		Mockito.verify(model, Mockito.times(1)).addAttribute("product", prod);
    }
}
