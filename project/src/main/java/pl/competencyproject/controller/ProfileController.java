package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends  BackController implements Initializable{
    private MainController mainController;

    @FXML
    private TextField profilNazwaUzytkownika;
    @FXML
    private TextField profilHaslo;
    @FXML
    private TextField profilNoweHaslo;
    @FXML
    private TextField profilPotwierdzHaslo;

    private static SessionLogon session = SessionLogon.getInstance();

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
        super.back(mainController, this);
    }

    @FXML
    public void logout(){
        mainController.loadLogonScreen();
        session.logOut();
    }

    private void setclockDate() {
        clock = SessionLogon.getInstance().getClockDate();
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
