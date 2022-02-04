package hr.algebra.pbadanjak.webshop.data.repository;

import com.google.firebase.auth.FirebaseAuthException;
import hr.algebra.pbadanjak.webshop.domain.beans.User;

import java.util.List;

public interface UserRepository {
    User getUserData(String uid) throws FirebaseAuthException;
    User updateUserData(User user) throws InterruptedException;
    Boolean deleteUser(User user) throws InterruptedException;
    List<User> getAllUsers() throws InterruptedException;
}
