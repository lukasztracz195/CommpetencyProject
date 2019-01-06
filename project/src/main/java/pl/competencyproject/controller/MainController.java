package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable , Runnable{

    @FXML
    private StackPane mainStackPane;

    public static final String Logon = "/fxmls/Logon.fxml";
    public static final String Menu = "/fxmls/MainMenuLayout.fxml";
    public static final String MainScreen = "/fxmls/MainScreen.fxml";
    public static final String Teaching = "/fxmls/Nauka.fxml";
    public static final String Dictionary = "/fxmls/Slownik.fxml";
    public static final String Test = "/fxmls/Test.fxml";
    public static final String Account = "/fxmls/Profil.fxml";
    public static final String Exam = "/fxmls/Exam.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadLogonScreen();
    }

    public void loadLogonScreen() {

        FXMLLoader loader = this.createLoader(MainController.Logon, this);
        Pane pane = MainController.createPane(loader);
        LogonController logonController = loader.getController();
        logonController.setMainController(this);
        setScreen(pane);
    }

    public static FXMLLoader createLoader(String fxml, Object toGetClass) {
        return new FXMLLoader(toGetClass.getClass().getResource(fxml));
    }

    public static Pane createPane(FXMLLoader loader) {
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }


    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }

    @Override
    public void run() {

    }
}

