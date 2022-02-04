package hr.algebra.pbadanjak.webshop.domain.beans.navigation;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class CartNavigation {

	public String getCart() {
		return "/cart/index.xhtml";
	}

	public String getCheckout() {
		return "/cart/checkout.xhtml";
	}

	public String getCheckoutSuccessful() {
		return "/cart/success.xhtml";
	}
}
