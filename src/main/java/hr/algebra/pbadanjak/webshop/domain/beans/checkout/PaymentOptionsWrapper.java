package hr.algebra.pbadanjak.webshop.domain.beans.checkout;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PaymentOptionsWrapper {

	public PaymentOptions[] getPaymentOptions() {
		return PaymentOptions.values();
	}
}
