package project.controllers.commands;

import java.util.ArrayList;
import java.util.List;

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
public class BuildAdminOrdersTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private ProductDAO productDAO;
    
    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private Command buildAdminOrders;

    @Test
    public void execute() throws Exception {
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		order.setCart(new ArrayList<Integer>());
		order.getCart().add(1);
		order.setUserId(1);
		orders.add(order);
		
		Mockito.when(orderDAO.getAll()).thenReturn(orders);
		Mockito.when(productDAO.get(1)).thenReturn(new Product());
		Mockito.when(userDAO.get(1)).thenReturn(new User());
		
		buildAdminOrders.execute(request, response, model);
		
		Mockito.verify(model).addAttribute(Mockito.eq("orders"), Mockito.anyList());
		Mockito.verify(model).addAttribute(Mockito.eq("products"), Mockito.anyMap());
		Mockito.verify(model).addAttribute(Mockito.eq("users"), Mockito.anyMap());
    }
}
