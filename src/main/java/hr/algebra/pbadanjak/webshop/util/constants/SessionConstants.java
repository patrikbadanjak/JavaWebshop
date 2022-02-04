package hr.algebra.pbadanjak.webshop.util.constants;

import jakarta.enterprise.context.SessionScoped;

public class SessionConstants {
    private SessionConstants() { }


    public static final String USER = "user";
    public static final String LOGGED_USER = "loggedUser";
    public static final String ADMIN = "admin";
    public static final String SESSION_TOKEN = "sessionToken";
}
