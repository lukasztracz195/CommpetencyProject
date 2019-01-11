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

    public static final String Logon = "/fxmls/LogonScene.fxml";
    public static final String Menu = "/fxmls/MainMenuScene.fxml";
    public static final String MainScreen = "/fxmls/GeneralScene.fxml";
    public static final String Dictionary = "/fxmls/DictionaryScene.fxml";
    public static final String PrepareExam = "/fxmls/PrepareExamScene.fxml";
    public static final String Account = "/fxmls/AccountScene.fxml";
    public static final String Exam = "/fxmls/ExamScene.fxml";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadLogonScreen();
    }

    public void loadLogonScreen() {

        FXMLLoader loader = createLoader(MainController.Logon, this);
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

