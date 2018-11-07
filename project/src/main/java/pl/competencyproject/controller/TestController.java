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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TestController implements Initializable {

    private MainController mainController;

    @FXML
    private TextField odpowiedz;

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    private Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clockDate();
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
    private void setDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        dateLabel.setText(dtf.format((now)));
    }

    @FXML
    public void logout(){
        mainController.loadLogonScreen();
        SessionLogon.logOut();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
