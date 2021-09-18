package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;

public interface Command {
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception;
}
