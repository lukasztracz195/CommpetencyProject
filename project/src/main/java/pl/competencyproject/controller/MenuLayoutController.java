package pl.competencyproject.controller;

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLayoutController extends AbstractController implements Initializable {

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
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
        /*
        FXMLLoader loader = MainController.createLoader(MainController.Menu,this);
        Pane pane = MainController.createPane(loader);
        AddClientController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
        */
    }

    @FXML
    public void nauka() {

        FXMLLoader loader = MainController.createLoader(MainController.Teaching, this);
        Pane pane = MainController.createPane(loader);
        TeachingController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void test() {

        FXMLLoader loader = MainController.createLoader(MainController.Test, this);
        Pane pane = MainController.createPane(loader);
        TestController controller = loader.getController();
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
}
