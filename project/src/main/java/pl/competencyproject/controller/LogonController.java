package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.ManageUsers;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.messages.Email;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codeLabel.setVisible(false);
        codeTextField.setVisible(false);
        codeTextField.setDisable(true);
        logOutButton.setDisable(true);
        codeLabel.setDisable(true);
        toggleButton.setId("LogIn");
        toggleButton.setText("<<<<");
        clockDate();
    }

    @FXML
    private void login() {
        SessionLogon.logIn(emailTextField.getText(), passwordTextField.getText());

        if (!SessionLogon.correctPassword) {
            passwordFeedbackLabel.setText("This password is worng");
        }
        if (SessionLogon.IdLoggedUser == -1) {
            emailFeedbackLabel.setText("User with this email not exist");
        }
        /*
// To trzeba zamienić na jedną linijkę
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
        emailTextField.clear();
        passwordTextField.clear();
        emailFeedbackLabel.setText("Użytkownik został zalogowany");
    }

    @FXML
    public void registration() {
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

    public void loginMenu() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/MainMenu.fxml"));
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
            if (SessionLogon.logged){
               // loginMenu();
            }
        } else {
            if (!approvesCode) {
                registration();
            } else {
                if (checkCode()) {
                    System.out.println(checkCode());
                    SessionLogon.sign(emailTextField.getText(), passwordTextField.getText());
                    emailFeedbackLabel.setText("Użytkownik został utowrzony");
                    logOutButton.setDisable(false);
                   // loginMenu();
                } else {
                    System.out.println(checkCode());
                    emailTextField.clear();
                    passwordTextField.clear();
                    emailFeedbackLabel.setText("Your code is wrong");
                }
            }
        }

    }

    public void changeToogleButton() {
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

    private boolean checkCode() {
        int code = Integer.valueOf(codeTextField.getText());
        if (code == SessionLogon.genereatedCode) {
            return true;
        }
        codeTextField.clear();
        return false;
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

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private boolean checkEmail(String email) {

        if (ManageUsers.existUser(email) == -1) {
            if (email.contains("@")) {
                String afterMonkey = email.substring(email.indexOf("@"));
                if (afterMonkey.contains(".")) {
                    return true;
                } else {
                    emailFeedbackLabel.setText("Podano nieprawidłowy adres email");
                    emailTextField.clear();
                    passwordTextField.clear();
                    return false;
                }
            } else {
                emailFeedbackLabel.setText("Podano nieprawidłowy adres email");
                emailTextField.clear();
                passwordTextField.clear();
                return false;
            }
        } else {
            emailFeedbackLabel.setText("Podana Nazwa Użytkownika już istnieje, zaloguj się");
            emailTextField.clear();
            passwordTextField.clear();
            return false;
        }
    }

    @FXML
    public void logOut(){
        SessionLogon.logOut();
        logOutButton.setDisable(true);
        emailFeedbackLabel.setText("Użytkownik został wylogowany");
    }
}
