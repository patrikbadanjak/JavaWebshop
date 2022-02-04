package hr.algebra.pbadanjak.webshop.domain.beans;

import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Set;

@ManagedBean
@RequestScoped
public class ImageUpload {
	private final String IMAGE_FOLDER = "src\\main\\webapp\\assets\\media\\images\\products\\";

	private Part uploadedImage;
	private String message;

	public void saveFile() {
		if (null != uploadedImage) {
			try {
				String fileName = IMAGE_FOLDER + uploadedImage.getName();
				uploadedImage.write(fileName);
				message = "Image uploaded successfully!";
			} catch (IOException ex) {
				ex.printStackTrace();
				message = "An error occurred.";
			}
		}
	}

	public Part getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(Part uploadedImage) {
		this.uploadedImage = uploadedImage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
