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

import project.config.SpringConfig;
import project.config.TestContext;
import project.DAO.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, SpringConfig.class})
@WebAppConfiguration
public class OrderSetPaidTest {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private HttpServletResponse response;
    
    @Autowired
    private Model model;
    
    @Autowired
    private OrderDAO orderDAO;
    
    @Autowired
    private Command orderSetPaid;

    @Test
    public void execute() throws Exception {
		String aim = "/final-spring/admin_orders";
		
		Mockito.when(request.getParameter("id")).thenReturn("3");
		
		orderSetPaid.execute(request, response, model);
		
		Mockito.verify(orderDAO).setState(3, "paid");
		Mockito.verify(response).sendRedirect(aim);
    }
}
