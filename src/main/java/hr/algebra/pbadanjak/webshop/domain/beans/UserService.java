package hr.algebra.pbadanjak.webshop.domain.beans;

import com.google.firebase.auth.FirebaseAuthException;
import hr.algebra.pbadanjak.webshop.data.repository.OrderRepository;
import hr.algebra.pbadanjak.webshop.data.repository.UserRepository;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;
import jakarta.faces.bean.ViewScoped;

import java.util.List;

@ManagedBean
@ViewScoped
public class UserService {
	private String userID;
	private User user;

	@ManagedProperty("#{firebaseUserRepositoryImpl}")
	private UserRepository userRepository;

	@ManagedProperty("#{orderRepositoryImpl}")
	private OrderRepository orderRepository;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		try {
			user = userRepository.getUserData(userID);
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
		this.userID = userID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setOrderRepository(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public List<Order> getUserOrders() {
		return orderRepository.getUserOrders(userID);
	}

}
