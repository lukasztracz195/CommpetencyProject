package pl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pl.competencyproject.model.DAO.ManageUsers;
import pl.competencyproject.model.Mutex;

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

        /*
        ManageUsers mu = new ManageUsers();
        System.out.println(mu.existUser("lukasztracz195@gmail.com","Ala ma kota").get(0).toString());
*/

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/logon.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("TeachingEnglishApp");
        primaryStage.setScene(new Scene(root, 794, 516));
        primaryStage.show();
    }

    @Override
    public void stop() {

    }


}

