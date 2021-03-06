package pl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.competencyproject.controller.MainController;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.Mutex;

import pl.competencyproject.model.timeClock.GeneralClock;
import pl.competencyproject.model.pollingMechanizm.DictionaryMap;
//import pl.competencyproject.model.pollingMechanizm.Teacher;

//import pl.competencyproject.model.timeClock.GeneralClock;
//
//import pl.competencyproject.model.mechanicsOfQuestion.ThreadBot;


//import pl.competencyproject.model.mechanicsOfQuestion.Teacher;
//
//
//
//import pl.competencyproject.model.dao.classes.ManageWordsENG;
//import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
//import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
//import pl.competencyproject.model.enums.TypeOfUsedDatabase;
//import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;
//import pl.competencyproject.model.mechanicsOfQuestion.Teacher;



import java.io.FileNotFoundException;
import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        Mutex mutex = new Mutex();
        boolean lockFile = mutex.lockInstance("LockFile");
        if (lockFile) {
            launch(args);
        }
    }

    @Override
    public void init() throws FileNotFoundException {
        SessionLogon.time = GeneralClock.getInstance();
        SessionLogon.IdLoggedUser = -1;
    }


    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MainController.MainScreen));
        StackPane root = loader.load();
        primaryStage.setTitle("TeachingEnglishApp");
        Scene scene = new Scene(root, 1280, 720 );//TO DO RESIZE APPLICATION
//         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        DictionaryMap dic = DictionaryMap.getInstance();
        dic.hardReset();
        SessionLogon.getInstance().logOut();

    }

}

