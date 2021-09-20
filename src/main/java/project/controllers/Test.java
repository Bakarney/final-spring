package project.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {

	@GetMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("Test servlet");
		return "Test";
	}
}
