package hr.algebra.pbadanjak.webshop.filters;

import hr.algebra.pbadanjak.webshop.domain.beans.Cart;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "CheckoutFilter", urlPatterns = "/cart/checkout.xhtml")
public class CheckoutFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		Cart cart = (Cart) session.getAttribute("cart");

		if (cart.getProducts().isEmpty()) {
			((HttpServletResponse) response).sendRedirect("/cart/index.xhtml");
		} else {
			chain.doFilter(request, response);
		}
	}
}
