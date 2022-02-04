package hr.algebra.pbadanjak.webshop.filters;

import hr.algebra.pbadanjak.webshop.domain.beans.LoggedUser;
import hr.algebra.pbadanjak.webshop.util.constants.SessionConstants;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "ProfileFilter", urlPatterns = "/profile/*")
public class ProfileFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		LoggedUser user = (LoggedUser) req.getSession().getAttribute(SessionConstants.LOGGED_USER);

		if (!user.getIsPresent()) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		} else if (user.isAdmin()) {
			req.getRequestDispatcher("/admin/index.xhtml").forward(req, res);
		} else {
			chain.doFilter(req, res);
		}
	}
}
