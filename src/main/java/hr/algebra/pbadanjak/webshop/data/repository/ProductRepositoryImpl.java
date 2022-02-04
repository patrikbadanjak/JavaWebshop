package hr.algebra.pbadanjak.webshop.data.repository;

import hr.algebra.pbadanjak.webshop.data.datasource.sql.SqlDataSourceSingleton;
import hr.algebra.pbadanjak.webshop.domain.beans.Category;
import hr.algebra.pbadanjak.webshop.domain.beans.Product;
import hr.algebra.pbadanjak.webshop.domain.beans.Brand;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ApplicationScoped;
import jakarta.faces.bean.ManagedBean;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@ManagedBean(eager = true)
@ApplicationScoped
public class ProductRepositoryImpl implements ProductRepository {

	DataSource dataSource;

	private final List<Product> products = new ArrayList<>();
	private final List<Category> categories = new ArrayList<>();

	private static final String GET_PRODUCTS = "{ CALL GetProducts }";
	private static final String GET_PRODUCT_COUNT = "{ CALL GetProductCount }";
	private static final String GET_PRODUCT = "{ CALL GetProduct(?) }";
	private static final String GET_PRODUCTS_FOR_CATEGORY = "{ CALL GetProductsForCategory(?) }";
	private static final String CREATE_PRODUCT = "{ CALL CreateProduct(?,?,?,?,?,?,?) }";
	private static final String UPDATE_PRODUCT = "{ CALL UpdateProduct(?,?,?,?,?,?,?) }";
	private static final String DELETE_PRODUCT = "{ CALL DeleteProduct(?) }";
	private static final String GET_CATEGORIES = "{ CALL GetCategories }";
	private static final String GET_CATEGORY_COUNT = "{ CALL GetCategoryCount }";


	private static final String GET_BRAND = "{ CALL GetBrand(?) }";
	private static final String GET_BRAND_BY_NAME = "{ CALL GetBrandByName(?) }";

	private static final String GET_CATEGORY = "{ CALL GetCategory(?) }";
	private static final String GET_CATEGORY_BY_NAME = "{ CALL GetCategoryByName(?) }";

    @PostConstruct
    private void init() {
		dataSource = SqlDataSourceSingleton.getInstance();
	}

	public List<Product> getProducts() {
		if (!products.isEmpty() && getProductCount() == products.size()) {
			return products;
		} else {
			products.clear();
			try (Connection con = dataSource.getConnection();
				 CallableStatement stmt = con.prepareCall(GET_PRODUCTS);) {

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {

						int brandID = rs.getInt(2);
						int categoryID = rs.getInt(4);

						Brand brand = getBrand(brandID);
						Category category = getCategory(categoryID);

						products.add(
							new Product(
								rs.getInt(1),
								brand,
								rs.getString(3),
								category,
								rs.getString(5),
								rs.getFloat(6),
								rs.getString(7)
							)
						);
					}
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return products;
	}

	@Override
	public Optional<Product> getProduct(int id) {
		Optional<Product> optionalProduct = Optional.empty();

		if (products.stream().noneMatch(product -> product.getId() == id)) {
			try (Connection con = dataSource.getConnection();
				 CallableStatement stmt = con.prepareCall(GET_PRODUCT);) {

				stmt.setInt(1, id);

				try (ResultSet rs = stmt.executeQuery()) {
					while (rs.next()) {

						int brandID = rs.getInt(2);
						int categoryID = rs.getInt(4);

						Brand brand = getBrand(brandID);
						Category category = getCategory(categoryID);

						optionalProduct = Optional.of(
							new Product(
								rs.getInt(1),
								brand,
								rs.getString(3),
								category,
								rs.getString(5),
								rs.getFloat(6),
								rs.getString(7)
							)
						);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			optionalProduct = products.stream().filter(product -> product.getId() == id).findFirst();
		}

		return optionalProduct;
	}

	@Override
	public List<Product> getProductsForCategory(int categoryID) {
		if (!products.isEmpty() && products.size() == getProductCount()) {
			return products.stream()
				.filter(product -> product.getCategory().getId() == categoryID)
				.collect(Collectors.toList());
		}

		List<Product> list = new ArrayList<>();

		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(GET_PRODUCTS_FOR_CATEGORY)) {

			stmt.setInt(1, categoryID);

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {

					int brandID = rs.getInt(2);
					int categoryId = rs.getInt(4);

					Brand brand = getBrand(brandID);
					Category category = getCategory(categoryId);

					list.add(
						new Product(
							rs.getInt(1),
							brand,
							rs.getString(3),
							category,
							rs.getString(5),
							rs.getFloat(6),
							rs.getString(7)
						)
					);
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

		return list;
	}

	@Override
	public boolean createProduct(Product product) {
		Brand brand = getBrandByName(product.getBrand().getName());
		Category category = getCategoryByName(product.getCategory().getName());

		product.setBrand(brand);
		product.setCategory(category);

		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(CREATE_PRODUCT)) {

			stmt.setInt(1, product.getBrand().getId());
			stmt.setString(2, product.getProduct());
			stmt.setInt(3, product.getCategory().getId());
			stmt.setString(4, product.getDescription());
			stmt.setFloat(5, (float) product.getPrice());
			stmt.setString(6, product.getImagePath());
			stmt.registerOutParameter(7, Types.INTEGER);

			stmt.executeUpdate();

			int id = stmt.getInt(7);

			products.add(new Product(id, product));

			return id > 0;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(Product product) {
		int index = products.indexOf(product);
		if (index != -1) {
			products.set(index, product);
		}

		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(UPDATE_PRODUCT)) {

			stmt.setInt(1, product.getId());
			stmt.setInt(2, product.getBrand().getId());
			stmt.setString(3, product.getProduct());
			stmt.setInt(4, product.getCategory().getId());
			stmt.setString(5, product.getDescription());
			stmt.setFloat(6, (float) product.getPrice());
			stmt.setString(7, product.getImagePath());

			return stmt.executeUpdate() > 0;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProduct(Product product) {
		return false;
	}

	@Override
	public List<Category> getCategories() {
		if (!categories.isEmpty() && categories.size() == getCategoryCount()) {
			return categories;
		}

		categories.clear();
		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(GET_CATEGORIES);) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					categories.add(
						new Category(
							rs.getInt(1),
							rs.getString(2)
						)
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}

		return categories;
	}

	private int getProductCount() {
		try	(Connection con = dataSource.getConnection();
			CallableStatement stmt = con.prepareCall(GET_PRODUCT_COUNT)) {

			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return 0;
	}

	private int getCategoryCount() {
		try	(Connection con = dataSource.getConnection();
			CallableStatement stmt = con.prepareCall(GET_CATEGORY_COUNT)) {

			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return 0;
	}

	private Brand getBrand(int brandID) {
		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(GET_BRAND);) {

			stmt.setInt(1, brandID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Brand(brandID, rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new Brand(-1, "");
	}

	@Override
	public Category getCategory(int categoryID) {
		if (!categories.isEmpty() && categories.size() == getCategoryCount()) {
			return categories.get(
				categories.indexOf(new Category(categoryID, ""))
			);
		}

		try (Connection con = dataSource.getConnection();
			 CallableStatement stmt = con.prepareCall(GET_CATEGORY);) {

			stmt.setInt(1, categoryID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Category(categoryID, rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new Category(-1, "");
	}

	public Brand getBrandByName(String brandName) {
		try (Connection con = dataSource.getConnection();
			CallableStatement stmt = con.prepareCall(GET_BRAND_BY_NAME)) {

			stmt.setString(1, brandName);
			ResultSet rs = stmt.executeQuery();
			rs.next();

			return new Brand(
				rs.getInt(1),
				rs.getString(2)
			);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return new Brand(-1, "");
	}

	public Category getCategoryByName(String categoryName) {
		try (Connection con = dataSource.getConnection();
			CallableStatement stmt = con.prepareCall(GET_CATEGORY_BY_NAME)) {

			stmt.setString(1, categoryName);
			ResultSet rs = stmt.executeQuery();
			rs.next();

			return new Category(
				rs.getInt(1),
				rs.getString(2)
			);
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}

		return new Category(-1, "");
	}
}
