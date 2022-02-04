package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.data.repository.OrderRepository;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.RequestScoped;

import java.util.List;

@ManagedBean
@RequestScoped
public class OrderService {
	private int orderID;
	private Order order;

	@ManagedProperty("#{orderRepositoryImpl}")
	private OrderRepository orderRepository;

	@ManagedProperty("#{loggedUser}")
	private LoggedUser user;

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		order = orderRepository.getOrder(orderID);
		this.orderID = orderID;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public void setUser(LoggedUser user) {
		this.user = user;
	}

	public List<Order> getOrdersForUser() {
		return orderRepository.getUserOrders(user.getUid());
	}
}
