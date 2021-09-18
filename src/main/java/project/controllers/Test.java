package project.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
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
	
	private ProducerDAO dao;
	
	@Autowired
	public Test(ProducerDAO dao) {
		this.dao = dao;
	}

	@GetMapping("/test")
	public String test(Model model) {
		System.out.println("Test servlet");
		return "Test";
	}
}
