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
public class CatalogChangePageTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private Command catalogChangePage;

    @Test
    public void executeIfqueryIsEmpty() throws Exception {
    	int change = 1;
    	int page = 1;
    	String query = null;
    	String aim = "/final-spring/catalog?page=2";
    	
    	Mockito.when(model.getAttribute("change")).thenReturn(change);
    	Mockito.when(request.getParameter("page")).thenReturn(String.valueOf(page));
    	Mockito.when(request.getQueryString()).thenReturn(query);
    	
		catalogChangePage.execute(request, response, model);
		
		Mockito.verify(response).sendRedirect(aim);
    }
    
    @Test
    public void executeIfqueryIsNotEmpty() throws Exception {
    	int change = 1;
    	int page = 5;
    	String query = "page=" + page;
    	String aim = "/final-spring/catalog?page=" + (page+change);
    	
    	Mockito.when(model.getAttribute("change")).thenReturn(change);
    	Mockito.when(request.getParameter("page")).thenReturn(String.valueOf(page));
    	Mockito.when(request.getQueryString()).thenReturn(query);
    	
		catalogChangePage.execute(request, response, model);
		
		Mockito.verify(response).sendRedirect(aim);
    }
}
