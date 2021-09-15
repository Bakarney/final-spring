package project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
	
	@GetMapping("/test")
	public String test() {
		System.out.println("Hello");
		return "Test";
	}
}
