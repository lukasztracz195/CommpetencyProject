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
    private TextField profilNowyEmail;
    @FXML
    private Label labelConfirmEmail;
    @FXML
    private TextField profilNowyEmail2;
    @FXML
    private TextField profilNoweHaslo;
    @FXML
    private Label labelConfirmPassword;
    @FXML
    private TextField profilPotwierdzHaslo;
    @FXML
    private TextField confirmCode;
    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);

        profilNazwaUzytkownika.setText(super.email);

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
        //System.out.println(!profilNoweHaslo.getText().trim().isEmpty());
        //System.out.println("nowe: "+profilNoweHaslo.getText());
        //System.out.println("potwierdz: "+profilPotwierdzHaslo.getText());
        //System.out.println(profilNoweHaslo.equals(profilPotwierdzHaslo));
        if(!profilNowyEmail.getText().trim().isEmpty()){
            if (profilNowyEmail.getText().equals(profilNowyEmail2.getText())){
                ManageUsers manageUsers = ManageUsers.getInstance();
                int id = manageUsers.existUser(super.email);
                manageUsers.updateEmail(id,profilNowyEmail.getText());
                //System.out.println("hdjjdfgsugdug");
            }
        } else if(!profilNoweHaslo.getText().trim().isEmpty()){
            if (profilNoweHaslo.getText().equals(profilPotwierdzHaslo.getText())) {
                //confirmPassword.setText("");
                ManageUsers manageUsers = ManageUsers.getInstance();
                int id = manageUsers.existUser(super.email);
                manageUsers.updatePasswordUser(id, profilPotwierdzHaslo.getText());
                //System.out.println("Haslo zostalo zmienione");
            } //confirmPassword.setText("Incorrect Confirm Password");
        } else if(!profilNowyEmail.getText().trim().isEmpty()&&!profilNoweHaslo.getText().trim().isEmpty()){
            if (profilNoweHaslo.getText().equals(profilPotwierdzHaslo.getText())&&profilNowyEmail.getText().equals(profilNowyEmail2.getText())) {
                ManageUsers manageUsers = ManageUsers.getInstance();
                int id = manageUsers.existUser(super.email);
                manageUsers.updateEmail(id, profilNowyEmail.getText());
                manageUsers.updatePasswordUser(id, profilPotwierdzHaslo.getText());
                //System.out.println("hsdvsvfhfsdfhdjs");
            }
        }
    }
    @FXML
    public void changeEmail(){
        profilNowyEmail.setVisible(true);
        labelConfirmEmail.setVisible(true);
        profilNowyEmail2.setVisible(true);
    }
    @FXML
    public void changePassword(){
        profilNoweHaslo.setVisible(true);
        labelConfirmPassword.setVisible(true);
        profilPotwierdzHaslo.setVisible(true);
    }
    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }

    public void setEmailPassword(String email) {
        profilNazwaUzytkownika.setText(email);

        super.email = email;
    }
}
