package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pl.competencyproject.model.DAO.classes.ManageUsers;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.messages.Email;

import java.net.URL;
import java.util.ResourceBundle;

public class LogonController extends AbstractController implements Initializable {

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

    private boolean statusLogin = true;
    private boolean approvesCode = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!SessionLogon.logged) {
            logOutButton.setDisable(true);
        }
        codeLabel.setVisible(false);
        codeTextField.setVisible(false);
        codeTextField.setDisable(true);
        logOutButton.setDisable(true);
        codeLabel.setDisable(true);
        toggleButton.setId("LogIn");
        toggleButton.setText("<<<<");
        super.setClockDate(clockLabel, dateLabel);

        super.email=emailTextField.getText();
    }

    private void login() {
        sessionLogon.logIn(emailTextField.getText(), passwordTextField.getText());
        clearAllFeedbackLabels();
        if (!SessionLogon.correctPassword) {
            passwordFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
            passwordFeedbackLabel.setText("This password is wrong");
        }
        if (SessionLogon.IdLoggedUser == -1) {
            emailFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
            emailFeedbackLabel.setText("User with this email not exist");
        }

        if (SessionLogon.logged) {
            logOutButton.setDisable(true);
            emailFeedbackLabel.setTextFill(new Color(1, 0, 0, 1));
            emailFeedbackLabel.setText("User with this email is logged just");
            clearFieldsEmailAndPassword();
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

        FXMLLoader loader = MainController.createLoader(MainController.Menu, this);
        Pane pane = MainController.createPane(loader);
        MenuLayoutController controller = loader.getController();
        controller.setEmailPassword(emailTextField.getText(),passwordTextField.getText());
        controller.setMainController(mainController);
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
                if (sessionLogon.checkCode(codeTextField.getText())) {

                    sessionLogon.sign(emailTextField.getText(), passwordTextField.getText());
                    clearAllFeedbackLabels();
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

    private boolean checkEmail(String email) {
        clearAllFeedbackLabels();
        if (ManageUsers.getInstance().existUser(email) == -1) {
            if (email.contains("@")) {
                String afterMonkey = email.substring(email.indexOf("@"));
                if (afterMonkey.contains(".")) {
                    return true;
                } else {
                    emailFeedbackLabel.setText("Wrong email");
                    clearFieldsEmailAndPassword();
                    return false;
                }
            } else {

                emailFeedbackLabel.setText("Wrong email");
                clearFieldsEmailAndPassword();
                return false;
            }
        } else {

            emailFeedbackLabel.setText("This email just exist in Database");
            clearFieldsEmailAndPassword();
            return false;
        }
    }

    @FXML
    public void logOut() {
        sessionLogon.logOut();
        clearAllFeedbackLabels();
        if (!SessionLogon.logged) {
            logOutButton.setDisable(true);
        }
        sessionLogon.closeSession();

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
