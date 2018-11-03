package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.ManageUsers;
import pl.competencyproject.model.messages.Email;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;

public class LogonController implements Initializable {

    private MainController mainController;

    @FXML
    private TextField LogNazwaUzytkownika;
    @FXML
    private TextField LogHaslo;
    @FXML
    private TextField RejNazwaUzytkownika;
    @FXML
    private TextField RejEmail;
    @FXML
    private TextField RejHaslo;
    @FXML
    private Label logErrorLabel;
    @FXML
    private Label rejErrorLabel;
    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button LogOutButton;
    private Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        LogOutButton.setDisable(true);
        clockDate();
    }
    @FXML
    public void login(){
//        ManageUsers manageUsers=new ManageUsers();
//        logErrorLabel.setText(" ");
//        boolean checker=true;
//
//        if(manageUsers.existUser(LogNazwaUzytkownika.getText())!=-1) {
//            if (manageUsers.checkUserPassword(manageUsers.existUser(LogNazwaUzytkownika.getText()), LogHaslo.getText())==checker) {
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
//            } else {
//                logErrorLabel.setText("Podano nieprawidłowe hasło");
//            }
//        } else{
//            logErrorLabel.setText("Podana Nazwa Użytkownika nie istnieje");
//        }
   }
    @FXML
    public void registration(){
        Email email=new Email();
        ManageUsers manageUsers=new ManageUsers();
        rejErrorLabel.setText("");
        CharSequence at="@";
        CharSequence dot=".";

        if(RejNazwaUzytkownika.getText().toLowerCase().contains(at)&&RejNazwaUzytkownika.getText().toLowerCase().contains(dot)) {
            if (manageUsers.existUser(RejNazwaUzytkownika.getText()) == -1) {
                email.mailRegestration(RejNazwaUzytkownika.getText());
            } else {
                rejErrorLabel.setText("Podana Nazwa Użytkownika już istnieje");
            }
        } else {
            rejErrorLabel.setText("Podano nieprawidłowy adres email");
        }
    }
/*
    @FXML
    public void exitApplication(ActionEvent event) {
        mainController.
        Platform.exit();
        timeline.stop();
    }
*/
    public void loginMenu(){
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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
