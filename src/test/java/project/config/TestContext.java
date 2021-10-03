package project.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;

import project.DAO.*;
import project.controllers.commands.Invoker;

@Configuration
public class TestContext {
	
	@Bean
	public HttpServletRequest request() {
		return Mockito.mock(HttpServletRequest.class);
	}
	
	@Bean
	public HttpServletResponse response() {
		return Mockito.mock(HttpServletResponse.class);
	}
	
	@Bean
	public HttpSession session() {
		return Mockito.mock(HttpSession.class);
	}
	
	@Bean
	public Model model() {
		return Mockito.mock(Model.class);
	}
	
	@Bean
	public Invoker invoker() {
		return Mockito.mock(Invoker.class);
	}
	
	@Bean
	public CategoryDAO categoryDAO() {
		return Mockito.mock(CategoryDAO.class);
	}
	
	@Bean
	public OrderDAO orderDAO() {
		return Mockito.mock(OrderDAO.class);
	}
	
	@Bean
	public ProducerDAO producerDAO() {
		return Mockito.mock(ProducerDAO.class);
	}
	
	@Bean
	public ProductDAO productDAO() {
		return Mockito.mock(ProductDAO.class);
	}
	
	@Bean
	public UserDAO userDAO() {
		return Mockito.mock(UserDAO.class);
	}
}
