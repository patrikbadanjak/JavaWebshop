package hr.algebra.pbadanjak.webshop;

import com.google.firebase.auth.FirebaseAuthException;
import hr.algebra.pbadanjak.webshop.di.RepositoryFactory;
import hr.algebra.pbadanjak.webshop.domain.beans.LoggedUser;
import hr.algebra.pbadanjak.webshop.domain.beans.User;
import hr.algebra.pbadanjak.webshop.data.repository.UserRepository;
import hr.algebra.pbadanjak.webshop.util.constants.FormConstants;
import hr.algebra.pbadanjak.webshop.util.constants.SessionConstants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "AuthServlet", value = "/auth")
public class AuthServlet extends HttpServlet {

	private final UserRepository userRepo = RepositoryFactory.getUserRepository();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		handleAuthRequest(request, response);
	}

	private void handleAuthRequest(HttpServletRequest request, HttpServletResponse response) {
		if (FormConstants.registerForm.equals(request.getParameter(FormConstants.formType))) {
			handleRegister(request, response);
		} else {
			handleSignIn(request, response);
		}

		String sessionToken = request.getParameter(SessionConstants.SESSION_TOKEN);

		request.getSession().setAttribute(SessionConstants.SESSION_TOKEN, sessionToken);

		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleSignIn(HttpServletRequest request, HttpServletResponse response) {
		String uid = request.getParameter(FormConstants.uid);

		try {
			User userData = userRepo.getUserData(uid);

            request.getSession(true).setAttribute(SessionConstants.LOGGED_USER, new LoggedUser(userData));
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}
	}

	private void handleRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			String uid = request.getParameter(FormConstants.uid);
			String userEmail = request.getParameter(FormConstants.email);
			String firstName = request.getParameter(FormConstants.firstName);
			String lastName = request.getParameter(FormConstants.lastName);

			String displayName = String.format("%s %s", firstName, lastName);

			User user = new User(uid, userEmail, displayName, false);

			user = userRepo.updateUserData(user);

			request.getSession(true).setAttribute(SessionConstants.LOGGED_USER, new LoggedUser(user));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
