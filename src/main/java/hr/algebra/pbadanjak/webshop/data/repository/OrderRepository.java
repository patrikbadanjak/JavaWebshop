package hr.algebra.pbadanjak.webshop.data.repository;

import hr.algebra.pbadanjak.webshop.domain.beans.Order;
import hr.algebra.pbadanjak.webshop.domain.beans.checkout.Checkout;

import java.util.List;

public interface OrderRepository {
	void processOrder(Checkout checkout, String userID);
	List<Order> getUserOrders(String uid);
	Order getOrder(int orderID);
}
