package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.ManageUsers;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.JavaFxLoader;
import pl.competencyproject.model.Time.GeneralClock;
import pl.competencyproject.model.messages.Email;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogonController implements Initializable {

    private MainController mainController;

    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField codeTextField;
    @FXML
    private Label emailFeedbackLabel;
    @FXML
    private Label passwordFeedbackLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label codeLabel;
    @FXML
    private ToggleButton toggleButton;
    @FXML
    private Button approvesButton;
    @FXML
    private Button logOutButton;

    private Timeline timeline;
    private boolean statusLogin = true;
    private boolean approvesCode = false;
    private GeneralClock clock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        codeLabel.setVisible(false);
        codeTextField.setVisible(false);
        codeTextField.setDisable(true);
        logOutButton.setDisable(true);
        codeLabel.setDisable(true);
        toggleButton.setId("LogIn");
        toggleButton.setText("<<<<");
        setclockDate();
    }

    private void login() {
        SessionLogon.logIn(emailTextField.getText(), passwordTextField.getText());
        clearAllFeedbackLabels();
        if (!SessionLogon.correctPassword) {
            passwordFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
            passwordFeedbackLabel.setText("This password is wrong");
        }
        if (SessionLogon.IdLoggedUser == -1) {
            emailFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
            emailFeedbackLabel.setText("User with this email not exist");
        }
        /*
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
        //******************************
        */
        logOutButton.setDisable(false);
        if (SessionLogon.logged) {
            emailFeedbackLabel.setTextFill(new Color(0, 1, 0, 1));
            emailFeedbackLabel.setText("Użytkownik został zalogowany");
        }
    }


    private void registration() {
        String email = emailTextField.getText();
        if (checkEmail(email)) {
            Email.mailRegestration(email);
            codeLabel.setVisible(true);
            codeLabel.setDisable(false);
            codeTextField.setVisible(true);
            codeTextField.setDisable(false);
            approvesButton.setText("Aproves your Code");
            this.approvesCode = true;
        }
    }

    private void loginMenu() {
        /*FXMLLoader loader=new FXMLLoader();
        JavaFxLoader javaFxLoader=new JavaFxLoader();
        Pane pane=null;
        pane=javaFxLoader.load("MainMenuLayout.fxml",loader);*/

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
    public void controlLogSignCheckButton() {
        if (statusLogin) {
            login();
            if (SessionLogon.logged) {
                 loginMenu();
            }
        } else {
            if (!approvesCode) {
                registration();
            } else {
                if (SessionLogon.checkCode(codeTextField.getText())) {

                    SessionLogon.sign(emailTextField.getText(), passwordTextField.getText());
                    clearAllFeedbackLabels();
                    emailFeedbackLabel.setText("Użytkownik został utowrzony");
                    logOutButton.setDisable(false);
                    loginMenu();
                } else {
                    codeTextField.clear();
                    clearFieldsEmailAndPassword();
                    clearAllFeedbackLabels();
                    emailFeedbackLabel.setText("Your code is wrong");
                }
            }
        }

    }

    public void changeToogleButton() {
        clearAllFeedbackLabels();
        clearFieldsEmailAndPassword();
        if (statusLogin) {
            statusLogin = false;
            toggleButton.setId("SignIn");
            approvesButton.setText("Sign in");
            emailTextField.clear();
            passwordTextField.clear();
            toggleButton.setText(">>>>");
        } else {
            statusLogin = true;
            emailTextField.clear();
            passwordTextField.clear();
            toggleButton.setText("<<<<");
            toggleButton.setId("LogIn");
            approvesButton.setText("Log in");
        }
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

    private boolean checkEmail(String email) {
        clearAllFeedbackLabels();
        if (ManageUsers.existUser(email) == -1) {
            if (email.contains("@")) {
                String afterMonkey = email.substring(email.indexOf("@"));
                if (afterMonkey.contains(".")) {
                    return true;
                } else {
                    emailFeedbackLabel.setText("Podano nieprawidłowy adres email");
                    clearFieldsEmailAndPassword();
                    return false;
                }
            } else {

                emailFeedbackLabel.setText("Podano nieprawidłowy adres email");
                clearFieldsEmailAndPassword();
                return false;
            }
        } else {

            emailFeedbackLabel.setText("Podana Nazwa Użytkownika już istnieje, zaloguj się");
            clearFieldsEmailAndPassword();
            return false;
        }
    }

    @FXML
    public void logOut() {
        SessionLogon.logOut();
        clearAllFeedbackLabels();
        logOutButton.setDisable(true);
        emailFeedbackLabel.setTextFill(new Color(0, 1, 0, 1));
        emailFeedbackLabel.setText("Użytkownik został wylogowany");
    }

    private void clearFieldsEmailAndPassword() {
        emailTextField.clear();
        passwordTextField.clear();
    }

    private void clearAllFeedbackLabels() {
        emailFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
        emailFeedbackLabel.setText("");
        passwordFeedbackLabel.setText("");
    }
}
