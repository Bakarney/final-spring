package project;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogingFilter implements Filter {
	
	static final Logger logger = LogManager.getLogger(LogingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		String url = req.getRequestURL().toString();
		String query = req.getQueryString();
		if (query != null)
			url += "?" + query;
		logger.info(url);
		chain.doFilter(request, response);
	}
}
