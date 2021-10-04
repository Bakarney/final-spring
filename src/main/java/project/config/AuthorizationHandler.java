package project.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class AuthorizationHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		String aim = "/login?error=wrong";
		
		Iterator<? extends GrantedAuthority> iter = authentication.getAuthorities().iterator();
		List<String> rolesNames = new ArrayList<>();
		
		while (iter.hasNext())
			rolesNames.add(iter.next().getAuthority().toString());
		
		if (rolesNames.contains("ROLE_ADMIN")) 
			aim = "/admin_catalog";
		else if (rolesNames.contains("ROLE_USER"))
			aim = "/profile";
		
		redirectStrategy.sendRedirect(request, response, aim);
	}
}
