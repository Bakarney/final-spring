package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * @author Naberezhniy Artur
 * 
 * Removing parameters "user" and "order" from session.
 */
@Component
public class SignOut implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("order");
		response.sendRedirect("/final-spring/home");
	}

}
