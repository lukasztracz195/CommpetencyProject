package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.competencyproject.model.DAO.SessionLogon;

import java.net.URL;
import java.util.ResourceBundle;

public class TestController extends AbstractController implements Initializable {

    @FXML
    private TextField odpowiedz;

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
    }

    @FXML
    public void back() {
        super.back(mainController, this);
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }
}
