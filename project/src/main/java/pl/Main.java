package pl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.competencyproject.model.DAO.ManageUsers;
import pl.competencyproject.model.DAO.SessionLogon;
import pl.competencyproject.model.Mutex;
import pl.competencyproject.model.messages.Email;

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
    public void start(Stage primaryStage) throws IOException {

        //SessionLogon.sign("lukasztracz195@gmail.com","zaq12wsx");
        SessionLogon.login("lukasztracz195@gmail.com","zaq12wsx");
        System.out.println("Id zalogowanego usera: "+
                SessionLogon.IdLoggedUser+" Czy hasło jego sie zgadza: "+
                SessionLogon.correctPassword+" Czy jest zalogowany: "+SessionLogon.logged);


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/logon.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("TeachingEnglishApp");
        primaryStage.setScene(new Scene(root, 794, 516));
        primaryStage.show();
    }

    @Override
    public void stop() {
        SessionLogon.logOut();
        System.out.println("Id wylogowanego usera: "+
                SessionLogon.IdLoggedUser+" Czy hasło jego sie zgadza: "+
                SessionLogon.correctPassword+" Czy jest zalogowany: "+SessionLogon.logged);
    }


}

