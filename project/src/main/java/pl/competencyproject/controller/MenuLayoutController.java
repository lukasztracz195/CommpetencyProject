package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MenuLayoutController {

    private MainController mainController;

    @FXML
    public void profil() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AddClient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    @FXML
    public void poziom() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AddClient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    @FXML
    public void slownik() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AddClient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    @FXML
    public void nauka() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Nauka.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TeachingController teachingController = loader.getController();
        teachingController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void test() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/test.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TestController testController = loader.getController();
        testController.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void statystyki() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/AddClient.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
