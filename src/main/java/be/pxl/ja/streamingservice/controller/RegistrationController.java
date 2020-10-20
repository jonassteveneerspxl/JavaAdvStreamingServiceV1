package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.StreamingService;
import be.pxl.ja.streamingservice.StreamingServiceFactory;
import be.pxl.ja.streamingservice.exception.DuplicateEmailException;
import be.pxl.ja.streamingservice.model.*;
import be.pxl.ja.streamingservice.util.PasswordUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button continueButton;

	@FXML
	private Button registerButton;

	@FXML
	private TextField firstnameTextField;

	@FXML
	private TextField lastnameTextField;

	@FXML
	private TextField cardnumberTextField;

	@FXML
	private TextField cvcTextField;

	@FXML
	private JFXComboBox<CreditCardType> creditCardTypeComboBox;

	@FXML
	private JFXComboBox<StreamingPlan> streamingPlanComboBox;

	@FXML
	private JFXDatePicker expirationDatePicker;

	@FXML
	private JFXProgressBar passwordStrengthIndicator;

	private Account newAccount;
	private StreamingService streamingService;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		streamingService = StreamingServiceFactory.getStreamingService();
		if (streamingPlanComboBox != null) {
			streamingPlanComboBox.setItems(FXCollections.observableList(Arrays.asList(StreamingPlan.values())));
		}
		if (creditCardTypeComboBox != null) {
			creditCardTypeComboBox.setItems(FXCollections.observableList(Arrays.asList(CreditCardType.values())));
		}
	}

	public void setAccount(Account newAccount) {
		this.newAccount = newAccount;
	}

	public void onContinue(ActionEvent actionEvent) {
		try {
			String email = emailTextField.getText();
			newAccount = new Account(email, passwordTextField.getText());
			StreamingPlan streamingPlan = streamingPlanComboBox.getValue();
			if (streamingPlan != null) {
				newAccount.setStreamingPlan(streamingPlan);
			}
			streamingService.addAccount(newAccount);
			URL resource = getClass().getClassLoader().getResource(Pages.REGISTRATION_STEP2);
			FXMLLoader loader = new FXMLLoader(resource);
			Stage stage = (Stage) continueButton.getScene().getWindow();
			Scene scene = new Scene(loader.load());
			RegistrationController controller = loader.getController();
			controller.setAccount(newAccount);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException | DuplicateEmailException e) {
			ErrorHandler.showError((RuntimeException) e);
		}
	}

	public void onPasswordUpdate(KeyEvent event) {
		int strength = PasswordUtil.calculateStrength(passwordTextField.getText());
		passwordStrengthIndicator.setProgress(strength / 10.0);
	}

	public void onRegister(ActionEvent actionEvent) {
		try {
			PaymentInfo paymentInfo = new PaymentInfo();
			CreditCardNumber cardNumber = new CreditCardNumber(cardnumberTextField.getText(), cvcTextField.getText());
			if (cardNumber.getType() != creditCardTypeComboBox.getValue()) {
				throw new IllegalArgumentException("Wrong credit card type.");
			}
			paymentInfo.setCardNumber(cardNumber);
			paymentInfo.setExpirationDate(expirationDatePicker.getValue());
			paymentInfo.setFirstName(firstnameTextField.getText());
			paymentInfo.setLastName(lastnameTextField.getText());
			newAccount.setPaymentInfo(paymentInfo);
			URL resource = getClass().getClassLoader().getResource(Pages.LOGIN_PAGE);
			Parent root = FXMLLoader.load(resource);
			Stage stage = (Stage) registerButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			ErrorHandler.showError(e);
		}
	}
}
