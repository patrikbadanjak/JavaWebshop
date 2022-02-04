package hr.algebra.pbadanjak.webshop.domain.beans.checkout;

import hr.algebra.pbadanjak.webshop.data.repository.OrderRepositoryImpl;
import hr.algebra.pbadanjak.webshop.domain.beans.Cart;
import hr.algebra.pbadanjak.webshop.domain.beans.LoggedUser;
import hr.algebra.pbadanjak.webshop.domain.beans.User;
import hr.algebra.pbadanjak.webshop.util.constants.SessionConstants;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class Checkout {
	private CustomerDetails customerDetails;
	private CustomerDetails deliveryDetails;
	private PaymentOptions paymentOptions = PaymentOptions.CASH;
	private DeliveryOptions deliveryOptions = DeliveryOptions.STANDARD;
	private boolean areDeliveryAndCustomerAddressEqual;

	@ManagedProperty("#{loggedUser}")
	private LoggedUser user;

	@ManagedProperty("#{cart}")
	private Cart cart;

	@ManagedProperty("#{orderRepositoryImpl}")
	private OrderRepositoryImpl repository;

	public void completeCheckout(ActionEvent actionEvent) {
		String userID = "guest";

		if (user.getIsPresent()) {
			userID = user.getUid();
		}

		repository.processOrder(this, userID);
		clearDetails();
	}

	public void clearDetails() {
		customerDetails = new CustomerDetails();
		deliveryDetails = new CustomerDetails();
		paymentOptions = PaymentOptions.CASH;
		deliveryOptions = DeliveryOptions.STANDARD;
		areDeliveryAndCustomerAddressEqual = false;
		cart.emptyCart();
	}

	public CustomerDetails getCustomerDetails() {
		if (customerDetails == null) {
			customerDetails = new CustomerDetails();
		}
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		if (customerDetails == null) {
			customerDetails = new CustomerDetails();
		}
		this.customerDetails = customerDetails;
	}

	public CustomerDetails getDeliveryDetails() {
		if (deliveryDetails == null) {
			deliveryDetails = new CustomerDetails();
		}
		return deliveryDetails;
	}

	public void setDeliveryDetails(CustomerDetails deliveryDetails) {
		if (deliveryDetails == null) {
			deliveryDetails = new CustomerDetails();
		}
		this.deliveryDetails = deliveryDetails;
	}

	public PaymentOptions getPaymentOptions() {
		return paymentOptions;
	}

	public void setPaymentOptions(PaymentOptions paymentOptions) {
		this.paymentOptions = paymentOptions;
	}

	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public boolean isAreDeliveryAndCustomerAddressEqual() {
		return areDeliveryAndCustomerAddressEqual;
	}

	public void setAreDeliveryAndCustomerAddressEqual(boolean areDeliveryAndCustomerAddressEqual) {
		if (customerDetails == null) {
			customerDetails = new CustomerDetails();
		}
		if (deliveryDetails == null) {
			deliveryDetails = new CustomerDetails();
		}
		this.areDeliveryAndCustomerAddressEqual = areDeliveryAndCustomerAddressEqual;
		if (areDeliveryAndCustomerAddressEqual) {
			this.deliveryDetails = customerDetails;
		}
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setRepository(OrderRepositoryImpl repository) {
		this.repository = repository;
	}

	public void setUser(LoggedUser user) {
		this.user = user;
	}
}
