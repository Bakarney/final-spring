package project.controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.controllers.commands.Command;

@Controller
public class FrontController {
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/catalog")
	public String getCatalog(HttpServletRequest request, Model model) {
		try {
			Command.execute(request, model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "catalog";
	}
}
