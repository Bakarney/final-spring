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
public class BuildProfileTest {
    
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
    private Command buildProfile;

    @Test
    public void executeIfBouthOrders() throws Exception {
    	String aim = "/final-spring/order_conflict";
    	String userName = "testName";
		User user = new User();
		Principal principal = Mockito.mock(Principal.class);
		Order sessionOrder = new Order();
		Order cloudOrder = new Order();
		
		Mockito.when(principal.getName()).thenReturn(userName);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(userName)).thenReturn(user);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("order")).thenReturn(sessionOrder);
		Mockito.when(orderDAO.get(user)).thenReturn(cloudOrder);
    	
		buildProfile.execute(request, response, model);
		
		Mockito.verify(model).addAttribute("user", user);
		Mockito.verify(response).sendRedirect(aim);
    }
    
    @Test
    public void executeIfOnlyCloudOrder() throws Exception {
    	String userName = "testName";
		User user = new User();
		Principal principal = Mockito.mock(Principal.class);
		Order sessionOrder = null;
		Order cloudOrder = new Order();
		
		Mockito.when(principal.getName()).thenReturn(userName);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(userName)).thenReturn(user);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("order")).thenReturn(sessionOrder);
		Mockito.when(orderDAO.get(user)).thenReturn(cloudOrder);
    	
		buildProfile.execute(request, response, model);
		
		Mockito.verify(model).addAttribute("user", user);
		Mockito.verify(session).setAttribute("order", cloudOrder);
    }
    
    @Test
    public void executeIfOnlySessionOrder() throws Exception {
    	String userName = "testName";
		User user = new User();
		Principal principal = Mockito.mock(Principal.class);
		Order sessionOrder = new Order();
		Order cloudOrder = null;
		
		Mockito.when(principal.getName()).thenReturn(userName);
		Mockito.when(request.getUserPrincipal()).thenReturn(principal);
		Mockito.when(userDAO.get(userName)).thenReturn(user);
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(session.getAttribute("order")).thenReturn(sessionOrder);
		Mockito.when(orderDAO.get(user)).thenReturn(cloudOrder);
    	
		buildProfile.execute(request, response, model);
		
		Mockito.verify(model).addAttribute("user", user);
		Mockito.verify(orderDAO).create(Mockito.anyInt());
		Mockito.verify(session).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
    }
}
