package hr.algebra.pbadanjak.webshop.domain.beans;

import hr.algebra.pbadanjak.webshop.data.repository.ProductRepository;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(forClass = Category.class)
public class CategoryConverter implements Converter<Category> {

	private ProductRepository repository;

	public CategoryConverter() {
		repository = (ProductRepository) FacesContext.getCurrentInstance().getExternalContext()
			.getApplicationMap().get("productRepositoryImpl");
	}

	@Override
	public Category getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}

		try {
			return repository.getCategory(Integer.parseInt(value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ConverterException(new FacesMessage(value + " is not a valid category ID"), e);
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Category category) {
		if (category == null) {
			return "";
		}
		return String.valueOf(category.getId());
	}

	public void setRepository(ProductRepository repository) {
		this.repository = repository;
	}
}
