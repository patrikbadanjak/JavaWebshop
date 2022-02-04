package hr.algebra.pbadanjak.webshop.di;

import hr.algebra.pbadanjak.webshop.data.repository.ProductRepository;
import hr.algebra.pbadanjak.webshop.data.repository.ProductRepositoryImpl;
import hr.algebra.pbadanjak.webshop.data.repository.FirebaseUserRepositoryImpl;
import hr.algebra.pbadanjak.webshop.data.repository.UserRepository;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;

import java.io.Serializable;

@SessionScoped
public class RepositoryFactory implements Serializable {

    @Produces
    @Default
    public static UserRepository getUserRepository() {
        return new FirebaseUserRepositoryImpl();
    }

    @Produces
    @Default
    public static ProductRepository getProductRepository() { return new ProductRepositoryImpl(); }
}
