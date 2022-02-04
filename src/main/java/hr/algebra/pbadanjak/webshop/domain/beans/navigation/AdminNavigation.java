package hr.algebra.pbadanjak.webshop.domain.beans.navigation;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class AdminNavigation {

	public String getAdmin() {
		return "/admin/index.xhtml";
	}

	public String getProducts() {
		return "/admin/products/index.xhtml";
	}

	public String getUsers() {
		return "/admin/users/index.xhtml";
	}

	public String getUserData() {
		return "/admin/users/user.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getOrderData() {
		return "/admin/users/order.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getEditProduct() {
		return "/admin/products/edit.xhtml?faces-redirect=true&includeViewParams=true";
	}

	public String getAddProduct() {
		return "/admin/products/add.xhtml?faces-redirect=true";
	}
}
