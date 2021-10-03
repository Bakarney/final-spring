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
public class BuildOrderConflictTest {
    
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
    private Command buildOrderConflict;

    @Test
    public void execute() throws Exception {
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(1);
		
		Mockito.when(request.getSession()).thenReturn(session);
		Mockito.when(request.getParameter("order_id")).thenReturn("1");
		Mockito.when(session.getAttribute("order")).thenReturn(order);
		Mockito.when(session.getAttribute("cloud_order")).thenReturn(order);
		Mockito.when(productDAO.get(0)).thenReturn(new Product());
		
		buildOrderConflict.execute(request, response, model);
		
		Mockito.verify(model).addAttribute(Mockito.eq("local_products"), Mockito.anyList());
		Mockito.verify(model).addAttribute(Mockito.eq("cloud_products"), Mockito.anyList());
    }
}
