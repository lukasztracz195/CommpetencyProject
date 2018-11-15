package pl.competencyproject.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public abstract class BackController {

    protected void back(MainController mainController, Object toGetClass) {
        FXMLLoader loader = MainController.createLoader(MainController.Menu, toGetClass);
        Pane pane = MainController.createPane(loader);
        MenuLayoutController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }
}
