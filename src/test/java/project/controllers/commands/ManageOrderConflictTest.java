package project.controllers.commands;

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
public class ManageOrderConflictTest {
    
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
    private Command manageOrderConflict;

	@Test
	public void manageCartsLocalTest() throws Exception {
		User user = new User();
		user.setId(1);
		Order localOrder = new Order();
		localOrder.setCart(new ArrayList<Integer>());
		Order cloudOrder = new Order();
		cloudOrder.setId(1);
		String aim = "/final-spring/profile";
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("command")).thenReturn("local");
		Mockito.when(session.getAttribute("user")).thenReturn(user);
		Mockito.when(session.getAttribute("order")).thenReturn(localOrder);
		Mockito.when(session.getAttribute("cloud_order")).thenReturn(cloudOrder);
		Mockito.when(orderDAO.create(user.getId())).thenReturn(new Order());
		
		manageOrderConflict.execute(request, response, model);
		
		Mockito.verify(orderDAO).delete(cloudOrder.getId());
		Mockito.verify(orderDAO).create(user.getId());
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(session, Mockito.atLeastOnce()).removeAttribute("cloud_order");
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
	
	@Test
	public void manageCartsCloudTest() throws Exception {
		String aim = "/final-spring/profile";
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("command")).thenReturn("cloud");
		
		manageOrderConflict.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).removeAttribute("cloud_order");
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
	
	@Test
	public void manageCartsCombineTest() throws Exception {
		Order localOrder = new Order();
		localOrder.setCart(new ArrayList<Integer>());
		localOrder.getCart().add(1);
		Order cloudOrder = new Order();
		cloudOrder.setCart(new ArrayList<Integer>());
		cloudOrder.getCart().add(2);
		String aim = "/final-spring/profile";
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("command")).thenReturn("combine");
		Mockito.when(session.getAttribute("order")).thenReturn(localOrder);
		Mockito.when(session.getAttribute("cloud_order")).thenReturn(cloudOrder);
		
		manageOrderConflict.execute(request, response, model);
		
		Mockito.verify(session, Mockito.atLeastOnce()).removeAttribute("cloud_order");
		Mockito.verify(session, Mockito.atLeastOnce()).setAttribute(Mockito.eq("order"), Mockito.any(Order.class));
		Mockito.verify(response, Mockito.atLeastOnce()).sendRedirect(aim);
	}
}
