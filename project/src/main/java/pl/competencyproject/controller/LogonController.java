package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class LogonController {

    private MainController mainController;

    @FXML
    private TextField LogNazwaUzytkownika;
    @FXML
    private TextField LogHaslo;
    @FXML
    private TextField RejNazwaUzytkownika;
    @FXML
    private TextField RejEmail;
    @FXML
    private TextField RejHaslo;


    @FXML
    public void logowanie() {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/MainMenuLayout.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MenuLayoutController menuLayoutController = loader.getController();
        menuLayoutController.setMainController(mainController);
        mainController.setScreen(pane);

    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
