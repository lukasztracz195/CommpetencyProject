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
    private PasswordField profilNoweHaslo;
    @FXML
    private Label labelConfirmPassword;
    @FXML
    private PasswordField profilPotwierdzHaslo;
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
    @FXML
    private Button accepAppChangesButton;

    private boolean showEmail = false;

    private boolean showPassword = false;

    private boolean showNewPasswordValue = false;

    private boolean showConfirmPasswordValue = false;

    private boolean saveIsActive = false;

    private static SessionLogon session = SessionLogon.getInstance();
    ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);


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
            manageUsers.deleteUser(SessionLogon.IdLoggedUser);
            manageUsers.reset();
            logout();
        }
    }

    @FXML
    public void save() {
//        if(!saveIsActive){
//        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
//        confirmation.setTitle("Aktualizacja konta");
//        confirmation.setContentText("Najpierw haslo z starego meila i do niego doklejasz z nowego ");
//        confirmation.setHeaderText("Wazne info!");
//        Optional<ButtonType> action = confirmation.showAndWait();}
//        if (action.get() == ButtonType.OK) {}
        if (!emailIsEmpty()) {
            updateEmailOnly();
        } else if (!passwordIsEmpty()) {
            updatePasswordOnly();
        }

        System.out.println("logilofi");

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

    private boolean updateQuestion(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Aktualizacja konta");
        confirmation.setContentText("Czy napewno chcesz zmiemić dane?");
        confirmation.setHeaderText("Potwierdzenie");
        Optional<ButtonType> action = confirmation.showAndWait();
        if (action.get() == ButtonType.OK){return true;}
        else return false;
    }

    private void updateEmailOnly() {
        if(saveIsActive == true){
             if(updateQuestion()){
                if (sessionLogon.checkCodeForEmailUpdate(Email.oldCode,Email.newCode)) {
                    manageUsers.updateEmail(SessionLogon.IdLoggedUser, profilNowyEmail.getText());
                    profilNazwaUzytkownika.setText("");
                    profilNazwaUzytkownika.setText(profilNowyEmail.getText());
                    updateSuccess();
                }
            }
            hideChangeEmail();
            saveIsActive = false;
        }else if (profilNowyEmail.getText().equals(profilNowyEmail2.getText())) {
            Email.mailChangeMail(profilNazwaUzytkownika.getText(),profilNowyEmail.getText());
            confirmCode.setVisible(true);
            saveIsActive = true;
        }else labelConfirmEmail.setText("Incorrect Confirm Email");


    }

    private void updatePasswordOnly() {
       if(saveIsActive == true)
       {
           Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
           confirmation.setTitle("Aktualizacja hasła");
           confirmation.setContentText("Czy napewno chcesz zmiemić hasło?");
           confirmation.setHeaderText("Potwierdzenie");
           Optional<ButtonType> action = confirmation.showAndWait();
           if (action.get() == ButtonType.OK) {
               if (sessionLogon.checkCode(confirmCode.getText())) {
                   manageUsers.updatePasswordUser(SessionLogon.IdLoggedUser, profilPotwierdzHaslo.getText());
                   updateSuccess();
                }
           }
           hideChangePassword();
           saveIsActive = false;

       }else if (profilNoweHaslo.getText().equals(profilPotwierdzHaslo.getText())) {
            Email.mailChangePassword(profilNazwaUzytkownika.getText());
             confirmCode.setVisible(true);
             saveIsActive = true;
        }else labelConfirmPassword.setText("Incorrect Confirm Password");
    }
    private void updateSuccess(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Aktualizacja konta");
        confirmation.setHeaderText("Wazne info!");
        confirmation.setContentText("Update success  ");
        Optional<ButtonType> action = confirmation.showAndWait();
        if (action.get() == ButtonType.OK) {}
    }
    private boolean emailIsEmpty() {
        if (profilNowyEmail.getText().trim().isEmpty() && profilNowyEmail2.getText().isEmpty()) {
            return true;
        } else return false;
    }

    private boolean passwordIsEmpty() {
        if (profilNoweHaslo.getText().trim().isEmpty()) {
            return true;
        } else return false;
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
        profilNoweHaslo.clear();
        confirmCode.setVisible(false);
        labelConfirmPassword.setVisible(false);
        profilPotwierdzHaslo.setVisible(false);
        profilPotwierdzHaslo.clear();
        confirmCode.setText("");
    }

    private void hideChangeEmail() {
        profilNowyEmail.setVisible(false);
        profilNowyEmail2.setVisible(false);
        profilNowyEmail.clear();
        profilNowyEmail2.clear();
        confirmCode.setVisible(false);
        labelConfirmEmail.setVisible(false);
        confirmCode.setText("");
    }

    private void approvesCode() {
        SessionLogon sessionLogon = SessionLogon.getInstance();
        if (sessionLogon.checkCode(confirmCode.getText())) {
            ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
            manageUsers.updatePasswordUser(SessionLogon.IdLoggedUser, profilPotwierdzHaslo.getText());
        } else {
            confirmCode.clear();

        }
    }


}
