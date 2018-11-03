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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clockDate();
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
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
