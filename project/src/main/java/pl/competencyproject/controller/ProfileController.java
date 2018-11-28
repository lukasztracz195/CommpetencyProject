package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.competencyproject.model.DAO.classes.ManageUsers;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController extends AbstractController implements Initializable {

    //private String Email;
    @FXML
    private Label profilNazwaUzytkownika;
    @FXML
    private Label profilHaslo;
    @FXML
    private TextField profilNoweHaslo;
    @FXML
    private TextField profilPotwierdzHaslo;
    @FXML
    private Label confirmPassword;
    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);

        profilNazwaUzytkownika.setText(super.email);
        profilHaslo.setText(super.password);
    }

    @FXML
    public void back() {
        super.back(mainController, this);
    }

    @FXML
    public void delete() {
        ManageUsers manageUsers = ManageUsers.getInstance();
        int id = manageUsers.existUser(super.email);
        manageUsers.deleteUser(id);
    }

    @FXML
    public void save() {
        if (profilNoweHaslo.equals(profilPotwierdzHaslo)) {
            confirmPassword.setText("");
            ManageUsers manageUsers = ManageUsers.getInstance();
            int id = manageUsers.existUser(super.email);
            manageUsers.updatePasswordUser(id, profilPotwierdzHaslo.getText());
        } else confirmPassword.setText("Incorrect Confirm Password");
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }

    public void setEmailPassword(String email, String password) {
        profilNazwaUzytkownika.setText(email);
        profilHaslo.setText(password);
        super.email = email;
    }
}
