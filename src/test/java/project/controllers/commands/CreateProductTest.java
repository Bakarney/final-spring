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
public class CreateProductTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private Command createProduct;

    @Test
    public void execute() throws Exception {
		String aim = "/final-spring/admin_catalog";
		
		Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("3");
		
		createProduct.execute(request, response, model);
		
		Mockito.verify(productDAO).create(Mockito.any(Product.class));
		Mockito.verify(response).sendRedirect(aim);
    }
}
