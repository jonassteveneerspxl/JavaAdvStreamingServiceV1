package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.StreamingService;
import be.pxl.ja.streamingservice.StreamingServiceFactory;
import be.pxl.ja.streamingservice.exception.AccountNotFoundException;
import be.pxl.ja.streamingservice.exception.InvalidPasswordException;
import be.pxl.ja.streamingservice.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController  implements Initializable {

	@FXML
	private Label lblErrors;

	@FXML
	private TextField txtUsername;

	@FXML
	private TextField txtPassword;

	@FXML
	private Button signInButton;

	private StreamingService streamingService;


	@Override
	public void initialize(URL url, ResourceBundle rb) {
		streamingService = StreamingServiceFactory.getStreamingService();
	}

	@FXML
	public void handleButtonAction(MouseEvent event) {

		if (event.getSource() == signInButton) {
			//login here
			Account account = logIn();
			if (account != null) {
				try {
					URL resource = getClass().getClassLoader().getResource(Pages.WHO_IS_WATCHING);
					FXMLLoader loader = new FXMLLoader(resource);
					Stage stage = (Stage) signInButton.getScene().getWindow();
					Scene scene = new Scene(loader.load());
					WhoIsWatchingController controller = loader.getController();
					controller.setAccount(account);
					stage.setScene(scene);

				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		}
	}

	private Account logIn() {
		String email = txtUsername.getText();
		String password = txtPassword.getText();
		if(email.isEmpty() || password.isEmpty()) {
			showError(Color.TOMATO, "Empty credentials");
		} else {
			Account account = null;
			try {
				account = streamingService.verifyAndGetAccount(email, password);
			} catch (AccountNotFoundException | InvalidPasswordException e) {
				showError(Color.TOMATO, "Wrong credentials.");
			}
			return account;
		}
		return null;
	}

	private void showError(Color color, String text) {
		lblErrors.setTextFill(color);
		lblErrors.setText(text);
		System.out.println(text);
	}

	public void onSignUp(ActionEvent actionEvent) {
		try {
			URL resource = getClass().getClassLoader().getResource(Pages.REGISTRATION_STEP1);
			Parent root = FXMLLoader.load(resource);
			Stage stage = (Stage) signInButton.getScene().getWindow();
			Scene scene = new Scene(root);
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void onSignIn(ActionEvent actionEvent) {
	}
}
