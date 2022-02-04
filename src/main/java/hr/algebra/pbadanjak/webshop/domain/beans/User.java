package hr.algebra.pbadanjak.webshop.domain.beans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ViewScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.util.Date;

@ManagedBean
@ViewScoped
public class User {
    private String uid;
    private String displayName;
    private String email;
    private Date lastSignIn;
    private Date accountCreationDate;
    private boolean isAdmin;

    public User() { }

    public User(String uid, String email, String displayName, boolean isAdmin, Date lastSignIn, Date accountCreationDate) {
        this(uid, email, isAdmin, lastSignIn, accountCreationDate);
        this.displayName = displayName;
    }

    public User(String uid, String email, boolean isAdmin, Date lastSignIn, Date accountCreationDate) {
        this(uid, email, "", isAdmin);
        this.lastSignIn = lastSignIn;
        this.accountCreationDate = accountCreationDate;
    }

    public User(String uid, String email, String displayName, boolean isAdmin) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.isAdmin = isAdmin;
    }

    public String getUid() {
        return uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public Date getLastSignIn() { return lastSignIn; }

    public Date getAccountCreationDate() { return accountCreationDate; }

    @Override
    public String toString() {
        return displayName;
    }
}
