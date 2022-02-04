package hr.algebra.pbadanjak.webshop.domain.beans.checkout;

public enum PaymentOptions {
	CASH("Cash"), PAYPAL("PayPal");

	private final String label;

	PaymentOptions(String label){
		this.label = label;
	}

	public String getLabel(){
		return this.label;
	}
}
