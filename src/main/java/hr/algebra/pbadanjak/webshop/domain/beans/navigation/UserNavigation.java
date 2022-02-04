package hr.algebra.pbadanjak.webshop.domain.beans.navigation;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UserNavigation {
    public String userProfile() {
        return "/profile/index.xhtml";
    }

    public String orderDetails() {
        return "/profile/orderDetails.xhtml?faces-redirect=true&includeViewParams=true";
    }
}
