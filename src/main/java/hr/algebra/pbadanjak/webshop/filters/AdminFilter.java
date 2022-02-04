package hr.algebra.pbadanjak.webshop.filters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import hr.algebra.pbadanjak.webshop.domain.beans.LoggedUser;
import hr.algebra.pbadanjak.webshop.domain.beans.User;
import hr.algebra.pbadanjak.webshop.util.constants.SessionConstants;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        LoggedUser user = (LoggedUser) req.getSession().getAttribute(SessionConstants.LOGGED_USER);
        String sessionToken = (String) req.getSession().getAttribute(SessionConstants.SESSION_TOKEN);

        if (user.getIsPresent()) {
            //if session token valid
            try {
                FirebaseAuth.getInstance().verifyIdToken(sessionToken);
                //not admin
                if (!user.isAdmin()) {
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (FirebaseAuthException e) {
                res.sendRedirect("/");
            }
        } else {
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        chain.doFilter(request, response);
    }
}
