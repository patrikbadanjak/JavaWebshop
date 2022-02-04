package hr.algebra.pbadanjak.webshop.domain.beans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@ManagedBean
@RequestScoped
public class LogoutBean {
    public void logout() throws IOException {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080");
    }
}
