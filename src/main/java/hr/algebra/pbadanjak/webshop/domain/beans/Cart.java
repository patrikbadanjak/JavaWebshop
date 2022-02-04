package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.util.Util;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

import java.util.ArrayList;
import java.util.List;

@ManagedBean(eager = true)
@SessionScoped
public class Cart {
	private List<Product> products;

	@PostConstruct
	private void init() {
		products = new ArrayList<>();
	}

	public List<Product> getProducts() {
		return products;
	}

	public void addProductToCart(Product product) {
		products.add(product);
	}

	public void removeProductFromCart(Product product) {
		products.remove(product);
	}

	public double getCartTotal() {
		return Util.round(products.stream().mapToDouble(Product::getPrice).sum(), 2);
	}

	public void emptyCart() {
		products.clear();
	}
}
