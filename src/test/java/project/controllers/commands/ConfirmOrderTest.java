package project.controllers.commands;

import java.security.Principal;

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
public class ConfirmOrderTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private HttpSession session;
    
    @Autowired
    private Model model;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private Command confirmOrder;

	@Test
	public void executeIfNoUserTest() throws Exception {
		String orderId = "1";
		Principal principal = Mockito.mock(Principal.class);
		User user = null;
		String aim = "/final-spring/sign_in";
		
		Mockito.when(request.getParameter("order_id")).thenReturn(orderId);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		
		confirmOrder.execute(request, response, model);
		
		Mockito.verify(response).sendRedirect(aim);
	}
	
	@Test
	public void confirmOrderIfUserTest() throws Exception {
		int orderId = 1;
		String finalState = "registrated";
		Principal principal = Mockito.mock(Principal.class);
		User user = new User();
		String aim = "/final-spring/home";
		
		Mockito.when(request.getParameter("order_id")).thenReturn(String.valueOf(orderId));
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(Mockito.anyString())).thenReturn(user);
		
		confirmOrder.execute(request, response, model);
		
		Mockito.verify(session).removeAttribute("order");
		Mockito.verify(orderDAO).setState(orderId, finalState);
		Mockito.verify(response).sendRedirect(aim);
	}
}
