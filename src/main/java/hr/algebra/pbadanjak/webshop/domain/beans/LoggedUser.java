package hr.algebra.pbadanjak.webshop.domain.beans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

@ManagedBean(name = "loggedUser")
@SessionScoped
public class LoggedUser extends User {
	private boolean isPresent;

	public LoggedUser() { }

	public LoggedUser(User user) {
		super(user.getUid(), user.getEmail(), user.getDisplayName(), user.isAdmin(), user.getLastSignIn(), user.getAccountCreationDate());
		isPresent = true;
	}

	public boolean getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(boolean present) {
		isPresent = present;
	}

	public void loadUserData() {

	}

	@Override
	public String toString() {
		return super.toString();
	}
}
