package hr.algebra.pbadanjak.webshop.domain.beans.navigation;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class ProductNavigation {
	public String getProductDetails() {
		return "/products/details.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getProducts() {
		return "/products/index.xhtml";
	}

	public String getProductsForCategory() {
		return "/products/category.xhtml?in";
	}
}
