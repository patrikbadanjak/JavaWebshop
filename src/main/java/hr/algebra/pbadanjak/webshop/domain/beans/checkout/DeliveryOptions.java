package hr.algebra.pbadanjak.webshop.domain.beans.checkout;

public enum DeliveryOptions {
	STANDARD("Standard");

	private final String label;

	DeliveryOptions(String label){
		this.label = label;
	}

	public String getLabel(){
		return this.label;
	}
}
