package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryController extends AbstractController implements Initializable {

    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
    }

    @FXML
    public void addDictionary(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv Files","*.csv"));

        List<File> selectedFiles=fileChooser.showOpenMultipleDialog(null);
        if(selectedFiles!=null){
            for(int i=0;i<selectedFiles.size();i++)
                selectedFiles.get(i).getAbsolutePath();
        }
    }
    @FXML
    public void back() {
        super.back(mainController, this);
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }
}
