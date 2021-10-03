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

import project.controllers.Test;

public class LogingFilter implements Filter {
	
	static final Logger logger = LogManager.getLogger(Test.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest)request).getRequestURL().toString();
		logger.info(url);
		chain.doFilter(request, response);
	}
}
