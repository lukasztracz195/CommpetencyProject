package pl.competencyproject.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class JavaFxLoader {
    public Pane load(String FXMLName,FXMLLoader loader){
        String FXMLSource="/fxmls/";
        String FullPath=FXMLSource+FXMLName;

        loader = new FXMLLoader(this.getClass().getResource(FullPath));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
