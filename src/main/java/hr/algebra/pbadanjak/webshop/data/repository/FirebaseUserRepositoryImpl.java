package hr.algebra.pbadanjak.webshop.data.repository;

import com.google.firebase.auth.*;
import hr.algebra.pbadanjak.webshop.di.FirebaseFactory;
import hr.algebra.pbadanjak.webshop.domain.beans.User;
import hr.algebra.pbadanjak.webshop.util.Util;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@ManagedBean(eager = true)
@ApplicationScoped
public class FirebaseUserRepositoryImpl implements UserRepository{

	private FirebaseAuth auth;

	public FirebaseUserRepositoryImpl() {
		auth = FirebaseFactory.provideFirebaseAuth();
	}

	@Override
	public User getUserData(String uid) throws FirebaseAuthException {
		UserRecord user = auth.getUser(uid);

		return new User(
			user.getUid(),
			user.getEmail(),
			user.getDisplayName(),
			(Boolean) user.getCustomClaims().get("admin"),
			Util.toDate(user.getUserMetadata().getLastSignInTimestamp()),
			Util.toDate(user.getUserMetadata().getCreationTimestamp())
		);
	}

	@Override
	public User updateUserData(User user) {
		CompletableFuture<User> completableFuture = new CompletableFuture<>();

		Map<String, Object> claims = new HashMap<>();
		claims.put("admin", user.isAdmin());

		Executors.newCachedThreadPool().submit(() -> {
			try {
				auth.setCustomUserClaims(user.getUid(), claims);

				UserRecord userRecord = auth.updateUser(
					new UserRecord.UpdateRequest(user.getUid())
						.setDisplayName(user.getDisplayName())
						.setEmail(user.getEmail())
				);

				completableFuture.complete(new User(user.getUid(), user.getEmail(), user.getDisplayName(), user.isAdmin(), Util.toDate(userRecord.getUserMetadata().getLastSignInTimestamp()), Util.toDate(userRecord.getUserMetadata().getCreationTimestamp())));
			} catch (FirebaseAuthException e) {
				e.printStackTrace();
			}
		});

		User updatedUser = new User();
		try	{
			updatedUser = completableFuture.get();
		} catch (ExecutionException | InterruptedException e) {
			e.printStackTrace();
		}

		return updatedUser;
	}

	@Override
	public Boolean deleteUser(User user) {
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();

		try {
			ListUsersPage listUsersPage = auth.listUsers(null);
			for (ExportedUserRecord userRecord :
				listUsersPage.iterateAll()) {
				users.add(new User(
					userRecord.getUid(),
					userRecord.getEmail(),
					userRecord.getDisplayName(),
					Boolean.getBoolean(userRecord.getCustomClaims().get("admin").toString()),
					Util.toDate(userRecord.getUserMetadata().getLastSignInTimestamp()),
					Util.toDate(userRecord.getUserMetadata().getCreationTimestamp())
				));
			}
		} catch (FirebaseAuthException e) {
			e.printStackTrace();
		}

		return users;
	}
}
