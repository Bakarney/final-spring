package project.controllers.commands;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CommandController {
	
	private Map<String, Command> commands;
	
	@Autowired
	public CommandController(Map<String, Command> commands) {
		this.commands = commands;
	}
	
	public void execute(String command, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			commands.get(command).execute(request, response, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
