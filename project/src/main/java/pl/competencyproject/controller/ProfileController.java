package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ProfileController implements Initializable{
    private MainController mainController;

    @FXML
    private TextField profilNazwaUzytkownika;
    @FXML
    private TextField profilHaslo;
    @FXML
    private TextField profilNoweHaslo;
    @FXML
    private TextField profilPotwierdzHaslo;

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    private Timeline timeline;
    private GeneralClock clock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setclockDate();
    }

    @FXML
    public void back() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/MainMenuLayout.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuLayoutController menuLayoutController = loader.getController();
        menuLayoutController.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void logout(){
        mainController.loadLogonScreen();
        SessionLogon.logOut();
    }

    private void setclockDate() {
        clock = SessionLogon.getClockDate();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> {
                    clockLabel.setText(clock.getTime());
                    dateLabel.setText(clock.getDate());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
