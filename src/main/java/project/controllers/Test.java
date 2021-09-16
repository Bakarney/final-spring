package project.controllers;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.DAO.*;

@Controller
public class Test {
	
	private final ProductDAO dao;
	
	@Autowired
	public Test(ProductDAO dao) {
		this.dao = dao;
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		System.out.println("Hello server");
		
		try {
			model.addAttribute("products", dao.getFiltered(null, null, null, null, null, null, null, null));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "Test";
	}
}
