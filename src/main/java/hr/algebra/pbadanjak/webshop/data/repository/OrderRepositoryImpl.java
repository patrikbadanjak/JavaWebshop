package hr.algebra.pbadanjak.webshop.data.repository;

import hr.algebra.pbadanjak.webshop.data.datasource.sql.SqlDataSourceSingleton;
import hr.algebra.pbadanjak.webshop.domain.beans.Order;
import hr.algebra.pbadanjak.webshop.domain.beans.Product;
import hr.algebra.pbadanjak.webshop.domain.beans.checkout.Checkout;
import hr.algebra.pbadanjak.webshop.domain.beans.checkout.PaymentOptions;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.ManagedProperty;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(eager = true)
@ApplicationScoped
public class OrderRepositoryImpl implements OrderRepository{

	DataSource dataSource;

	@ManagedProperty("#{productRepositoryImpl}")
	private ProductRepository productRepository;

	@PostConstruct
	public void init() {
		dataSource = SqlDataSourceSingleton.getInstance();
	}

	private static final String CREATE_ORDER = "{ CALL CreateOrder(?, ?, ?, ?, ?, ?, ?) }";
	private static final String CREATE_PRODUCT_ORDER = "{ CALL CreateProductOrder(?, ?, ?, ?) }";
	private static final String GET_ORDERS_FOR_USER = "{ CALL GetOrdersForUser(?) }";
	private static final String GET_ORDER = "{ CALL GetOrder(?) }";
	private static final String GET_PRODUCTS_FOR_ORDER = "{ CALL GetProductsForOrder(?) }";

	@Override
	public void processOrder(Checkout checkout, String userID) {
		try (Connection con = dataSource.getConnection();
			 CallableStatement orderStmt = con.prepareCall(CREATE_ORDER);
			 CallableStatement productOrderStmt = con.prepareCall(CREATE_PRODUCT_ORDER)) {

			orderStmt.setDate(1, Date.valueOf(LocalDate.now()));
			orderStmt.setString(2, checkout.getCustomerDetails().toString());
			orderStmt.setString(3, checkout.getDeliveryDetails().toString());
			orderStmt.setInt(4, checkout.getPaymentOptions() == PaymentOptions.CASH ? 1 : 2);
			orderStmt.setFloat(5, (float) checkout.getCart().getCartTotal());
			orderStmt.setString(6, userID);
			orderStmt.registerOutParameter(7, Types.INTEGER);

			orderStmt.executeUpdate();

			int orderID = orderStmt.getInt(7);

			for (Product product:
				 checkout.getCart().getProducts()) {
				productOrderStmt.setInt(1, product.getId());
				productOrderStmt.setInt(2, orderID);
				productOrderStmt.setInt(3, 1);
				productOrderStmt.setFloat(4, (float) product.getPrice());

				productOrderStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getUserOrders(String uid) {
		List<Order> orders = new ArrayList<>();

		try (Connection con = dataSource.getConnection();
			 CallableStatement orderStmt = con.prepareCall(GET_ORDERS_FOR_USER)) {

			orderStmt.setString(1, uid);

			ResultSet rs = orderStmt.executeQuery();

			while (rs.next()) {
				orders.add(
					new Order(
						rs.getInt("ID"),
						rs.getDate("ODate"),
						rs.getString("UserDetails"),
						rs.getString("DeliveryAddress"),
						rs.getInt("PaymentMethodID") == 1 ? PaymentOptions.CASH : PaymentOptions.PAYPAL,
						rs.getFloat("Total"),
						rs.getString("UserID"),
						getProductsForOrder(rs.getInt("ID"))
					)
				);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return orders;
	}

	@Override
	public Order getOrder(int orderID) {
		try (Connection con = dataSource.getConnection();
			 CallableStatement orderStmt = con.prepareCall(GET_ORDER)) {

			orderStmt.setInt(1, orderID);

			ResultSet rs = orderStmt.executeQuery();

			rs.next();

			return new Order(
				rs.getInt("ID"),
				rs.getDate("ODate"),
				rs.getString("UserDetails"),
				rs.getString("DeliveryAddress"),
				rs.getInt("PaymentMethodID") == 1 ? PaymentOptions.CASH : PaymentOptions.PAYPAL,
				rs.getFloat("Total"),
				rs.getString("UserID"),
				getProductsForOrder(rs.getInt("ID"))
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private List<Product> getProductsForOrder(int orderID) {
		List<Product> products = new ArrayList<>();

		try (Connection con = dataSource.getConnection();
			 CallableStatement orderStmt = con.prepareCall(GET_PRODUCTS_FOR_ORDER)) {

			orderStmt.setInt(1, orderID);

			ResultSet rs = orderStmt.executeQuery();

			while (rs.next()) {
				products.add(
					productRepository.getProduct(rs.getInt(2)).get()
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
}
