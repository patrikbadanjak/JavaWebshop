package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.util.Util;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.bean.ViewScoped;

import java.util.Objects;

@ManagedBean(eager = true)
@ViewScoped
public class Product {
	private int id;
	private Brand brand;
	private String product;
	private Category category;
	private String description;
	private double price;
	private String imagePath;

	@PostConstruct
	public void init() {
		brand = new Brand();
		category = new Category();
	}

	public Product() {
		brand = new Brand();
		category = new Category();
	}

	public Product(int id, Brand brand, String product, Category category, String description, double price, String imagePath) {
		this.id = id;
		this.brand = brand;
		this.product = product;
		this.category = category;
		this.description = description;
		this.price = Util.round(price, 2);
		this.imagePath = imagePath;
	}

	public Product(int id, Product product) {
		this.id = id;
		this.brand = product.getBrand();
		this.product = product.getProduct();
		this.category = product.getCategory();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.imagePath = product.getImagePath();
	}

	public int getId() {
		return id;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = Util.round(price, 2);
	}

	public String getImagePath() {
		return null == imagePath ?
			"" :
			!imagePath.isEmpty() ? imagePath : "/assets/media/images/products/no_image.png";
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return id == product.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return brand.getName() + ' ' + product;
	}
}
