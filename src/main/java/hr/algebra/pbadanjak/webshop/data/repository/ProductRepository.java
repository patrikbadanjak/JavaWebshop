package hr.algebra.pbadanjak.webshop.data.repository;

import hr.algebra.pbadanjak.webshop.domain.beans.Category;
import hr.algebra.pbadanjak.webshop.domain.beans.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> getProducts();
    Optional<Product> getProduct(int id);
    List<Product> getProductsForCategory(int categoryID);
    boolean createProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(Product product);
    List<Category> getCategories();
    Category getCategory(int id);
}
