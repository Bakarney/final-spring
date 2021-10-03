package project.controllers.commands;

import java.security.Principal;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
public class BuildCartTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private HttpSession session;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private Command buildCart;

	@Test
	public void buildIfUserTest() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		User user = new User();
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(1);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		Mockito.when(orderDAO.get(user)).thenReturn(order);
		for (Integer prod : order.getCart()) 
			Mockito.when(productDAO.get(prod)).thenReturn(new Product());
		
		buildCart.execute(request, response, model);
		
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute(Mockito.eq("products"), Mockito.anyList());
	}
	
	@Test
	public void buildIfNoUserButSessionIsOrderTest() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		User user = null;
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(1);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		for (Integer prod : order.getCart()) 
			Mockito.when(productDAO.get(prod)).thenReturn(new Product());
		
		buildCart.execute(request, response, model);
		
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute(Mockito.eq("products"), Mockito.anyList());
	}
	
	@Test
	public void buildIfNoUserAndNoOrderTest() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		User user = null;
		Order order = null;
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		
		buildCart.execute(request, response, model);
		
		Mockito.verify(model, Mockito.atLeastOnce()).addAttribute(Mockito.eq("products"), Mockito.anyList());
	}
}
