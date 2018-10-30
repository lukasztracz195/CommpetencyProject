import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.project_competencies.connection.SessionFactoryConfig;

import java.math.BigInteger;

public class Main extends Application {


    public static void main(String[] args) {
        String configFileName = "hibernate.cfg.xml";
        SessionFactoryConfig.getSessionFactory(configFileName);
        launch(args);
        SessionFactoryConfig.getSessionFactory(configFileName).close();
        BigInteger bi = new BigInteger("5");
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
