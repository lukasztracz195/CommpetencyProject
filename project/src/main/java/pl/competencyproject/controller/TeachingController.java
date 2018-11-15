package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TeachingController extends BackController implements Initializable {

    private MainController mainController;

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    private Timeline timeline;

    private static SessionLogon session = SessionLogon.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clockDate();
    }

    @FXML
    public void back() {
        super.back(mainController, this);
    }

    private void clockDate() {
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> setClock()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void setClock() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        clockLabel.setText(dtf.format((now)));
        setDate();
    }

    private void setDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText(dtf.format((now)));
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        session.logOut();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
