package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.ManageUsers;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.messages.Email;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfileController extends AbstractController implements Initializable {

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
    private Button buttonChangeEmail;
    @FXML
    private Button buttonChangePassword;
    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;

    private boolean showEmail = false;

    private boolean showPassword = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);

        profilNazwaUzytkownika.setText(SessionLogon.email);

    }

    @FXML
    public void back() {
        super.back(mainController, this);
    }

    @FXML
    public void delete() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Usuwanie konta");
        confirmation.setContentText("Czy napewno chcesz usunąć konto?");
        confirmation.setHeaderText("Potwierdzenie");
        Optional<ButtonType> action = confirmation.showAndWait();
        if (action.get() == ButtonType.OK) {
            ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
            manageUsers.deleteUser(SessionLogon.IdLoggedUser);
            SessionLogon.IdLoggedUser = -1;
            mainController.loadLogonScreen();
        }
    }

    @FXML
    public void save() {
        if (!profilNowyEmail.getText().trim().isEmpty()) {
            if (profilNowyEmail.getText().equals(profilNowyEmail2.getText())) {
                ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
                manageUsers.updateEmail(SessionLogon.IdLoggedUser, profilNowyEmail.getText());
            }
        } else if (!profilNoweHaslo.getText().trim().isEmpty()) {
            if (profilNoweHaslo.getText().equals(profilPotwierdzHaslo.getText())) {
                //confirmPassword.setText("");
                Email.mailChangePassword(profilNowyEmail.getText());
                confirmCode.setVisible(true);
                SessionLogon sessionLogon = SessionLogon.getInstance();
                if (sessionLogon.checkCode(confirmCode.getText())) {
                    ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
                    manageUsers.updatePasswordUser(SessionLogon.IdLoggedUser, profilPotwierdzHaslo.getText());
                }
            }
            labelConfirmPassword.setText("Incorrect Confirm Password");
        } else if (!profilNowyEmail.getText().trim().isEmpty() && !profilNoweHaslo.getText().trim().isEmpty()) {
            if (profilNoweHaslo.getText().equals(profilPotwierdzHaslo.getText()) && profilNowyEmail.getText().equals(profilNowyEmail2.getText())) {
                ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
                manageUsers.updateEmail(SessionLogon.IdLoggedUser, profilNowyEmail.getText());
                manageUsers.updatePasswordUser(SessionLogon.IdLoggedUser, profilPotwierdzHaslo.getText());
            }
        }
    }

    @FXML
    public void changeEmail() {
        if (!showEmail) {
            if (showPassword) {
                hideChangePassword();
                cleanChangePassword();
                showPassword = false;
            }
            showChangeEmail();
            showEmail = true;
        }
    }

    @FXML
    public void changePassword() {
        if (!showPassword) {
            if (showEmail) {
                hideChangeEmail();
                clearChangeEmail();
                showEmail = false;
            }
            showChangePassword();
            showPassword = true;
        }
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }

    private void cleanChangePassword() {
        profilNoweHaslo.clear();
        profilPotwierdzHaslo.clear();
    }

    private void clearChangeEmail() {
        profilNowyEmail.clear();
        profilNowyEmail2.clear();
    }

    private void showChangePassword() {
        profilNoweHaslo.setVisible(true);
        labelConfirmPassword.setVisible(true);
        profilPotwierdzHaslo.setVisible(true);
    }

    private void showChangeEmail() {
        profilNowyEmail.setVisible(true);
        profilNowyEmail2.setVisible(true);
        labelConfirmEmail.setVisible(true);
    }

    private void hideChangePassword() {
        profilNoweHaslo.setVisible(false);
        labelConfirmPassword.setVisible(false);
        profilPotwierdzHaslo.setVisible(false);
    }

    private void hideChangeEmail() {
        profilNowyEmail.setVisible(false);
        profilNowyEmail2.setVisible(false);
        labelConfirmEmail.setVisible(false);
    }


}
