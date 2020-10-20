package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.StreamingService;
import be.pxl.ja.streamingservice.StreamingServiceFactory;
import be.pxl.ja.streamingservice.model.Account;
import be.pxl.ja.streamingservice.model.Content;
import be.pxl.ja.streamingservice.model.Profile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	private StreamingService streamingService;

	@FXML
	private ScrollPane movieGrid;
	@FXML
	private ImageView profileImage;

	private Account account;
	private Profile currentProfile;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		System.out.println("Initializing");
		streamingService = StreamingServiceFactory.getStreamingService();
	}

	private void showContent(Collection<Content> contentList) {
		GridPane contentGrid = new GridPane();
		int row = 0;
		int col = 0;
		for (Content content: contentList) {
			if (currentProfile.allowedToWatch(content)) {
				Image image = new Image("streamingservice/images/" + content.getImageUrl());
				ImageView contentImage = new ImageView(image);
				contentImage.setFitHeight(220.0);
				contentImage.setFitWidth(150.0);
				contentImage.setOnMouseClicked((e) -> {
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Pages.CONTENT_DETAIL));
						Parent parent = fxmlLoader.load();
						ContentDetailController contentDetailController = fxmlLoader.getController();
						contentDetailController.setData(content, currentProfile);
						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.setScene(scene);
						stage.showAndWait();
					} catch (IOException f) {
						f.printStackTrace();
					}
				});
				contentGrid.add(contentImage, col, row);
				col++;
				if (col == 3) {
					col = 0;
					row++;
				}
			}
		}
		movieGrid.setContent(contentGrid);
	}

	public void setAccount(Account account, Profile profile) {
		this.account = account;
		this.currentProfile = profile;
		profileImage.setImage(new Image("streamingservice/images/" + profile.getAvatar() + ".png"));
		showContent(streamingService.getContentList());
	}

	public void onProfileUpdate(MouseEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(Pages.UPDATE_PROFILE));
			Parent parent = fxmlLoader.load();
			UpdateProfileController dialogController = fxmlLoader.getController();
			dialogController.setProfile(currentProfile);

			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);
			stage.showAndWait();
			showContent(streamingService.getContentList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onLogout(MouseEvent event) {
		try {
			//setAccount(null);
			URL resource = getClass().getClassLoader().getResource(Pages.LOGIN_PAGE);
			FXMLLoader loader = new FXMLLoader(resource);
			Stage stage = (Stage) movieGrid.getScene().getWindow();
			Scene scene = new Scene(loader.load());
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void showMyList(MouseEvent mouseEvent) {
		showContent(currentProfile.getMyList());
	}

	public void showCurrentlyWatching(MouseEvent mouseEvent) {
		showContent(currentProfile.getCurrentlyWatching());
	}

	public void showRecentlyWatched(MouseEvent mouseEvent) {
		showContent(currentProfile.getRecentlyWatched());
	}

	public void showAvailableContent(MouseEvent mouseEvent) {
		showContent(streamingService.getContentList());
	}

}
