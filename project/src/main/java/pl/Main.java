package pl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Mutex;
import pl.competencyproject.model.Time.GeneralClock;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main extends Application {


    public static void main(String[] args) throws FileNotFoundException {
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

//       int id =  ManageUsers.existUser("test@examle.pl");
//       ManageUsers.deleteUser(id);




        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/Logon.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("TeachingEnglishApp");
        Scene scene = new Scene(root, 794, 516);
        // scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        SessionLogon.logOut();
        System.out.println("Id wylogowanego usera: " +
                SessionLogon.IdLoggedUser + " Czy hasło jego sie zgadza: " +
                SessionLogon.correctPassword + " Czy jest zalogowany: " + SessionLogon.logged);
    }


}

