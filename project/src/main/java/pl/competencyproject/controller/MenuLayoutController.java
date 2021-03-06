package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.ManageDictionaryWords;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLayoutController extends AbstractController implements Initializable {

    //private String email1;
    //private String password1;

    @FXML
    private Label witaj;
    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        if(SessionLogon.email != null) {
            String[] welcome = SessionLogon.email.split("@");
            String s = "Witaj " + welcome[0].trim();
            witaj.setText(s);
        }
        ManageDictionaryWords MDW = new ManageDictionaryWords(TypeOfUsedDatabase.OnlineOrginalDatabase);
    }

    @FXML
    public void profil() {

        FXMLLoader loader = MainController.createLoader(MainController.Account, this);
        Pane pane = MainController.createPane(loader);
        ProfileController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void poziom() {
        /*
        FXMLLoader loader = MainController.createLoader(MainController.Menu,this);
        Pane pane = MainController.createPane(loader);
        AddClientController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
        */
    }

    @FXML
    public void slownik() {

        FXMLLoader loader = MainController.createLoader(MainController.Dictionary,this);
        Pane pane = MainController.createPane(loader);
        DictionaryController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void test() {

        FXMLLoader loader = MainController.createLoader(MainController.PrepareExam, this);
        Pane pane = MainController.createPane(loader);
        PrepareExamController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void statystyki() {
        /*
        FXMLLoader loader = MainController.createLoader(MainController.Menu,this);
        Pane pane = MainController.createPane(loader);
        AddClientController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
        */
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }
    /*public void setUserData(){
        super.email=email1;
        super.password=password1;
    }*/
    /*public void setEmailPassword(String email,String password){
        super.email=email;

        String[] welcome=email.split("@");
        String s="Witaj "+welcome[0].trim();
        witaj.setText(s);
    }*/
}
