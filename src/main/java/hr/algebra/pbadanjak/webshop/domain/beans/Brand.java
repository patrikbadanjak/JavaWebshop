package hr.algebra.pbadanjak.webshop.domain.beans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.bean.ViewScoped;

import java.util.Objects;

@ManagedBean(eager = true)
@ViewScoped
public class Brand {
	private int id;
	private String name;

	public Brand() { }

	public Brand(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Brand brand = (Brand) o;
		return id == brand.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return name;
	}
}
