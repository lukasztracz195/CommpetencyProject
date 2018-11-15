package pl.competencyproject.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Time.GeneralClock;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuLayoutController implements Initializable{

    private MainController mainController;

    @FXML
    private Label clockLabel;

    @FXML
    private Label dateLabel;

    private Timeline timeline;

    private GeneralClock clock;

    private SessionLogon session = SessionLogon.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setclockDate();
    }
    @FXML
    public void profil() {

        FXMLLoader loader = MainController.createLoader(MainController.Account,this);
        Pane pane = MainController.createPane(loader);
        ProfileController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void poziom() {
/*
        FXMLLoader loader = MainController.createLoader(MainController.Menu,this);
        Pane pane = MainController.createPane(loader);
        AddClientController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
        */

    }

    @FXML
    public void slownik() {

/*
        FXMLLoader loader = MainController.createLoader(MainController.Menu,this);
        Pane pane = MainController.createPane(loader);
        AddClientController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
        */
    }

    @FXML
    public void nauka() {

        FXMLLoader loader = MainController.createLoader(MainController.Teaching,this);
        Pane pane = MainController.createPane(loader);
        TeachingController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void test() {

        FXMLLoader loader = MainController.createLoader(MainController.Test, this);
        Pane pane = MainController.createPane(loader);
        TestController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);

    }

    @FXML
    public void statystyki() {

//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/AddClient.fxml"));
//        Pane pane = null;
//        try {
//            pane = loader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        AddClientController addClientController=loader.getController();
//        addClientController.setMainController(mainController);
//        mainController.setScreen(pane);

    }

    private void setclockDate() {
        clock = session.getClockDate();
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000),
                ae -> {
                    clockLabel.setText(clock.getTime());
                    dateLabel.setText(clock.getDate());
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void logout(){
        mainController.loadLogonScreen();
        session.logOut();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


}
