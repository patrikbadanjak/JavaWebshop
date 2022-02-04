package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.domain.beans.checkout.PaymentOptions;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

import java.sql.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class Order {
	private int orderID;
	private Date orderDate;
	private String userDetails;
	private String deliveryAddress;
	private PaymentOptions paymentMethod = PaymentOptions.CASH;
	private float total;
	private String userID;
	private List<Product> products;

	public Order() { }

	public Order(int orderID, Date orderDate, String userDetails, String deliveryAddress, PaymentOptions paymentMethod, float total, String userID, List<Product> products) {
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.userDetails = userDetails;
		this.deliveryAddress = deliveryAddress;
		this.paymentMethod = paymentMethod;
		this.total = total;
		this.userID = userID;
		this.products = products;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(String userDetails) {
		this.userDetails = userDetails;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public PaymentOptions getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentOptions paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
