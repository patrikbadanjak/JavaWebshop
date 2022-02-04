package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.data.repository.ProductRepositoryImpl;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.event.ActionEvent;

@ManagedBean
@ViewScoped
public class ProductService {
	private int productID;
	private int categoryID;

	private Product product;

	@ManagedProperty("#{productRepositoryImpl}")
	private ProductRepositoryImpl repository;

	@PostConstruct
	public void init() {
		product = new Product();
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		repository.getProduct(productID).ifPresent(product -> this.product = product);
		this.productID = productID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setRepository(ProductRepositoryImpl repository) {
		this.repository = repository;
	}

	public void updateProduct(ActionEvent event) {
		repository.updateProduct(product);
	}

	public void addNewProduct(ActionEvent event) {
		repository.createProduct(product);
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
}
