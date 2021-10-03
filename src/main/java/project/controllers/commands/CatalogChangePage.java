package project.controllers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class CatalogChangePage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		int change = (int) model.getAttribute("change");
		
		String pageStr = request.getParameter("page");
		
		int page = (pageStr != null) ? Integer.valueOf(pageStr) : 1;
		
		String query = request.getQueryString();
		if (query == null) 
			query = "";
		else 
			query = query.replaceAll("&?page=-?\\d+", "");
		if (!query.isEmpty()) query += "&";
		query += "page=" + (page+change);
		
		response.sendRedirect("/final-spring/catalog?" + query);
	}
}
