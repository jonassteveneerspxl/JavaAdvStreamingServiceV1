package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.exception.TooManyProfilesException;
import be.pxl.ja.streamingservice.model.Account;
import be.pxl.ja.streamingservice.model.Profile;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WhoIsWatchingController implements Initializable {

	@FXML
	ImageView imageProfile1;
	@FXML
	ImageView imageProfile2;
	@FXML
	ImageView imageProfile3;
	@FXML
	ImageView imageProfile4;

	@FXML
	Label labelProfile1;
	@FXML
	Label labelProfile2;
	@FXML
	Label labelProfile3;
	@FXML
	Label labelProfile4;

	@FXML
	JFXButton addProfileButton;


	private Account account;
	private Profile profile;


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public void setAccount(Account account) {
		init();
		this.account = account;
		updateProfiles();
	}

	public void updateProfiles() {
		enableProfile(imageProfile1, labelProfile1, getProfile(0));
		enableProfile(imageProfile2, labelProfile2, getProfile(1));
		enableProfile(imageProfile3, labelProfile3, getProfile(2));
		enableProfile(imageProfile4, labelProfile4, getProfile(3));
	}

	private Profile getProfile(int i) {
		if (i < account.getProfiles().size()) {
			return account.getProfiles().get(i);
		}
		return null;
	}

	public void addProfile(ActionEvent actionEvent) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Pages.UPDATE_PROFILE));
			Parent parent = fxmlLoader.load();
			UpdateProfileController dialogController = fxmlLoader.getController();
			int newIndex = account.getNumberOfProfiles() + 1;
			Profile newProfile = new Profile("Profile " + newIndex, "profile" + newIndex);
			account.addProfile(newProfile);
			dialogController.setProfile(newProfile);

			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			updateProfiles();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TooManyProfilesException e) {
			ErrorHandler.showError(e);
		}
	}

	public void enableProfile(ImageView image, Label label, Profile profile) {
		if (profile == null) {
			return;
		}
		image.setImage(new Image("streamingservice/images/" + profile.getAvatar() + ".png"));
		image.setVisible(true);
		image.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				try {
					URL resource = getClass().getClassLoader().getResource(Pages.MAIN_PAGE);
					FXMLLoader loader = new FXMLLoader(resource);
					Stage stage = (Stage) addProfileButton.getScene().getWindow();
					Scene scene = new Scene(loader.load());
					MainController controller = loader.getController();
					controller.setAccount(account, profile);
					stage.setScene(scene);

				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		label.setText(profile.getName());
		label.setVisible(true);
	}

	public void init() {
		imageProfile1.setVisible(false);
		imageProfile2.setVisible(false);
		imageProfile3.setVisible(false);
		imageProfile4.setVisible(false);
		labelProfile1.setVisible(false);
		labelProfile2.setVisible(false);
		labelProfile3.setVisible(false);
		labelProfile4.setVisible(false);
	}
}
