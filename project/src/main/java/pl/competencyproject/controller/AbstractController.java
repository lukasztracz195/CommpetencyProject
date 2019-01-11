package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

public abstract class AbstractController {

     MainController mainController;
    private Timeline timeline;
    private GeneralClock clock;
     SessionLogon sessionLogon = SessionLogon.getInstance();

    protected void back(MainController mainController, Object toGetClass) {
        FXMLLoader loader = MainController.createLoader(MainController.Menu, toGetClass);
        Pane pane = MainController.createPane(loader);
        MenuLayoutController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

     void setClockDate(Label clockLabel, Label dateLabel) {
        clock = sessionLogon.getClockDate();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> {
                    clockLabel.setText(clock.getTime());
                    dateLabel.setText(clock.getDate());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

     void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
