package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import project.DAO.*;
import project.entities.*;

@Component
public class BuildAdminProduct implements Command {
	
	private ProductDAO productDAO;

	@Autowired
	public BuildAdminProduct(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int id = Integer.valueOf(request.getParameter("id"));
		Product prod = productDAO.get(id);
		model.addAttribute("product", prod);
	}

}
