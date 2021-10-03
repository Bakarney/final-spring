package project.controllers.commands;

import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
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
public class OrderAddProductTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private HttpSession session;
    
    @Autowired
    private Model model;
    
    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private Command orderAddProduct;

	@Test
	public void addProductWithoutUserTest() throws Exception {
		User user = null;
		Principal principal = Mockito.mock(Principal.class);
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		String productId = "1"; 
		String aim = "/final-spring/product?id=" + productId;
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		Mockito.when(request.getParameter("product_id")).thenReturn(productId);
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		
		orderAddProduct.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
	
	@Test
	public void addProductWithUserTest() throws Exception {
		User user = new User();
		Principal principal = Mockito.mock(Principal.class);
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		String productId = "1"; 
		String aim = "/final-spring/product?id=" + productId;
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("product_id")).thenReturn(productId);
		Mockito.when(orderDAO.get(user)).thenReturn(order);
		
		orderAddProduct.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
}
