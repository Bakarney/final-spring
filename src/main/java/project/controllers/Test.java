package project.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.DAO.*;
import project.config.SpringConfig;
import project.entities.*;

@Controller
public class Test {
	
	@GetMapping("/test")
	public String test(Model model) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		ProductDAO dao = context.getBean(ProductDAO.class);
		context.close();
		System.out.println("Test servlet");
		try {
			System.out.println(dao.getFiltered(null, null, null, null, null, null, null, null));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Test";
	}
}
