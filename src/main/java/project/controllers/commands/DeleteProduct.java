package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;

@Component
public class DeleteProduct implements Command {
	
	private ProductDAO productDAO;

	@Autowired
	public DeleteProduct(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		productDAO.delete(Integer.valueOf(request.getParameter("id")));
    	System.out.println("Here");
    	response.sendRedirect("/final-spring/admin_catalog");
	}

}
