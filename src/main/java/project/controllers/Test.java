package project.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.DAO.*;
import project.entities.*;

@Controller
public class Test {
	
	private final OrderDAO dao;
	
	@Autowired
	public Test(OrderDAO dao) {
		this.dao = dao;
	}
	
	@GetMapping("/test")
	public String test(Model model) {
		System.out.println("Test servlet");
		return "Test";
	}
}
