package pl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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




        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxmls/logon.fxml"));
        AnchorPane root = loader.load();
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 794, 516));
        primaryStage.show();
    }

    @Override
    public void stop() {

    }


}

