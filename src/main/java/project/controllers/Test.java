package project.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import project.DAO.*;
import project.config.SpringConfig;
import project.controllers.commands.CommandController;
import project.entities.*;

@Controller
public class Test {
	
	private CommandController command;
	
	@Autowired
	public Test(CommandController command) {
		this.command = command;
	}

	@GetMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("Test servlet");
		command.execute("command", request, response, model);
		return "Test";
	}
}
