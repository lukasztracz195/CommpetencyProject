package pl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.competencyproject.controller.MainController;
import pl.competencyproject.model.CSV.CSVReader;
import pl.competencyproject.model.CSV.LibraryCSV;
import pl.competencyproject.model.DAO.ManageLevels;
import pl.competencyproject.model.DAO.ManageWordsENG;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Mutex;
import pl.competencyproject.model.Time.GeneralClock;
import pl.competencyproject.model.entities.Level;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class Main extends Application {

    public static void main(String[] args) throws FileNotFoundException {
        //CSVReader.SelectCSV("ENG PL");
        Mutex mutex = new Mutex();
        boolean lockFile = mutex.lockInstance("LockFile");
        if (lockFile) {
            launch(args);
        }
    }

    @Override
    public void init() {
        SessionLogon.time = GeneralClock.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(MainController.MainScreen));
        StackPane root = loader.load();
        primaryStage.setTitle("TeachingEnglishApp");
        Scene scene = new Scene(root, 794, 516);
//         scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        SessionLogon.getInstance().logOut();
        SessionLogon.getInstance().closeSession();
    }

}

