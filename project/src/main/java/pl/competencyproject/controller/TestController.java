package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TestController {

    private MainController mainController;
    private LogonController logonController;

    @FXML
    private TextField odpowiedz;


    @FXML
    public void back() {
        logonController.logowanie();
    }


    public void setLogonController(LogonController logonController) {
        this.logonController = logonController;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
