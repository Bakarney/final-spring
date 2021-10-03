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
public class OrderRemoveProductTest {
    
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
    private Command orderRemoveProduct;

	@Test
	public void removeProductWithoutDBTest() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		Order order = new Order();
		order.setId(1);
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(3);
		order.getCart().add(4);
		String productId = "1"; 
		String aim = "/final-spring/cart";
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getParameter("id")).thenReturn(productId);
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		Mockito.when(orderDAO.get(1)).thenReturn(order);
		
		orderRemoveProduct.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
	
	@Test
	public void removeProductWithDBTest() throws Exception {
		Principal principal = Mockito.mock(Principal.class);
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(3);
		order.getCart().add(4);
		String productId = "3"; 
		String aim = "/final-spring/cart";
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(request.getParameter("id")).thenReturn(productId);
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		
		orderRemoveProduct.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
}
