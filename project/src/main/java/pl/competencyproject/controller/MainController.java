package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane mainStackPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadLogonScreen();
    }

    public void loadLogonScreen() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/logon.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogonController logonController = loader.getController();
        logonController.setMainController(this);
        setScreen(pane);
    }

    public void setScreen(Pane pane) {
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(pane);
    }

}

