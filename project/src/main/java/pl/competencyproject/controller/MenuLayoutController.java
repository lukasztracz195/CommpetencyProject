package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MenuLayoutController implements Initializable{

    private MainController mainController;

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
    public void profil() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/Profil.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProfileController profileController=loader.getController();
        profileController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void poziom() {

//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/AddClient.fxml"));
//        Pane pane = null;
//        try {
//            pane = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    @FXML
    public void slownik() {

//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/AddClient.fxml"));
//        Pane pane = null;
//        try {
//            pane = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    @FXML
    public void nauka() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/Nauka.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TeachingController teachingController = loader.getController();
        teachingController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void test() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/test.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestController testController = loader.getController();
        testController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void statystyki() {

//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/AddClient.fxml"));
//        Pane pane = null;
//        try {
//            pane = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

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

    @FXML
    public void logout(){
        mainController.loadLogonScreen();
        SessionLogon.logOut();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
