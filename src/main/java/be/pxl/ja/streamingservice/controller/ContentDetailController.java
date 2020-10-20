package be.pxl.ja.streamingservice.controller;

import be.pxl.ja.streamingservice.model.Content;
import be.pxl.ja.streamingservice.model.Movie;
import be.pxl.ja.streamingservice.model.Playable;
import be.pxl.ja.streamingservice.model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class ContentDetailController {

	@FXML
	private Label titleLabel;

	@FXML
	private Label durationLabel;

	@FXML
	private Label nowPlayingLabel;

	@FXML
	private FontIcon playIcon;

	@FXML
	private FontIcon pauseIcon;

	@FXML
	private FontIcon genreIcon;

	@FXML
	private FontIcon inMyListIcon;

	@FXML
	private FontIcon addToMyListIcon;

	@FXML
	private Button finishedButton;

	private Profile profile;
	private Content content;

	public void onPlay(MouseEvent mouseEvent) {
		profile.startWatching(content);
		nowPlayingLabel.setVisible(true);
		playIcon.setVisible(false);
		pauseIcon.setVisible(true);
		finishedButton.setVisible(true);
	}

	public void onPause(MouseEvent mouseEvent) {
		nowPlayingLabel.setVisible(false);
		playIcon.setVisible(true);
		pauseIcon.setVisible(false);
		finishedButton.setVisible(false);

	}

	public void onFinish(MouseEvent mouseEvent) {
		profile.finishWatching(content);
		Stage stage = (Stage) finishedButton.getScene().getWindow();
		stage.close();
	}

	public void addToMyList(MouseEvent mouseEvent) {
		profile.addToMyList(content);
		updateMyListIcons();
	}

	public void removeFromMyList(MouseEvent mouseEvent) {
		profile.removeFromMyList(content);
		updateMyListIcons();
	}

	public void setData(Content content, Profile profile) {
		this.content = content;
		this.profile = profile;
		titleLabel.setText(content.toString());
		playIcon.setVisible(content instanceof Playable);
		durationLabel.setVisible(content instanceof Playable);
		updateMyListIcons();
		if (content instanceof Movie) {
			Movie movie = (Movie) content;
			durationLabel.setText(movie.getPlayingTime());

			String icon = "mdi-comment-question-outline";
			if (movie.getGenre() != null) {
				icon = movie.getGenre().getIcon();
			}
			genreIcon.setIconLiteral(icon);
		}
	}

	private void updateMyListIcons() {
		boolean inMyList = profile.isInMyList(content);
		addToMyListIcon.setVisible(!inMyList);
		inMyListIcon.setVisible(inMyList);
	}

}
