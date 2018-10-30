package pl.competencyproject.controller;

import javafx.fxml.FXML;

public class TeachingController {

    private MainController mainController;
    private LogonController logonController;


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
