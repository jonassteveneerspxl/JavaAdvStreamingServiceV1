package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.exception.InvalidDateException;
import be.pxl.ja.streamingservice.model.Profile;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.time.LocalDate;

public class UpdateProfileController {

	@FXML
	private DatePicker dateOfBirthDatePicker;

	@FXML
	private JFXTextField profileTextField;

	@FXML
	private ImageView profileImage;

	private Profile profile;

	public void setProfile(Profile profile) {
		this.profile = profile;
		profileImage.setImage(new Image("streamingservice/images/" + profile.getAvatar() + ".png"));
		profileTextField.setText(profile.getName());
		dateOfBirthDatePicker.setValue(profile.getDateOfBirth());
	}

	public void onUpdate(ActionEvent actionEvent) {
		profile.setName(profileTextField.getText());
		try {
			LocalDate dob = dateOfBirthDatePicker.getValue();
			if (dob == null) {
				throw new IllegalArgumentException("Date of birth required.");
			}
			profile.setDateOfBirth(dob);

			Stage stage = (Stage) ((JFXButton) actionEvent.getSource()).getScene().getWindow();
			stage.close();
		} catch (IllegalArgumentException | InvalidDateException e) {
			ErrorHandler.showError(e);
		}
	}
}
