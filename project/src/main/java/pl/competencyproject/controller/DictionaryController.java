package pl.competencyproject.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DictionaryController extends AbstractController implements Initializable {

    @FXML
    private ChoiceBox typeOfDictionaryChoiceBox;
    @FXML
    private ChoiceBox nameOfLevelChoiceBox;
    @FXML
    private ChoiceBox nameOfCategoryChoiceBox;
    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        setChoiceBoxFields();
        enableChoiceBoxes();
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
    public void clearChoice(){
        typeOfDictionaryChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(null);
        nameOfCategoryChoiceBox.setDisable(true);
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

    private void setChoiceBoxFields(){
        typeOfDictionaryChoiceBox.getItems().add("B2");

        nameOfLevelChoiceBox.getItems().addAll("1","2","3");

        nameOfCategoryChoiceBox.getItems().addAll("Category1","Category2","Category3");
    }
    private void enableChoiceBoxes(){
        typeOfDictionaryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->nameOfLevelChoiceBox.setDisable(false));
        nameOfLevelChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->nameOfCategoryChoiceBox.setDisable(false));
    }
}
