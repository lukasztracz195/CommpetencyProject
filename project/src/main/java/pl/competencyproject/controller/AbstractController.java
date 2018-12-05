package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

public abstract class AbstractController {

    protected MainController mainController;
    private Timeline timeline;
    private GeneralClock clock;
    protected SessionLogon sessionLogon = SessionLogon.getInstance();
    protected String email;

    protected void back(MainController mainController, Object toGetClass) {
        FXMLLoader loader = MainController.createLoader(MainController.Menu, toGetClass);
        Pane pane = MainController.createPane(loader);
        MenuLayoutController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

    protected void setClockDate(Label clockLabel, Label dateLabel) {
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
   /* protected void setWelcome(Label witaj){
        String[] welcome=email.split("@");
        String s="Witaj "+welcome[0].trim();
        witaj.setText(s);
    }*/

    protected void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
