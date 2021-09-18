package project.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import project.DAO.*;

import project.controllers.commands.Command;
import project.controllers.commands.CommandController;

@Controller
public class FrontController {
	
	private CommandController command;
	
	@Autowired
	public FrontController(CommandController command) {
		this.command = command;
	}
	
	@GetMapping("/home")
	public String getHome() {
		return "home";
	}
	
	@GetMapping("/catalog")
	public String getCatalog(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			command.buildCatalog(request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "catalog";
	}
	
	@GetMapping("/catalog_next")
	public void getNextPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("change", 1);
			command.catalogNext(request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/catalog_prev")
	public void getPrevPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			model.addAttribute("change", -1);
			command.catalogNext(request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("/product")
	public String getProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			command.buildProduct(request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product";
	}
}
