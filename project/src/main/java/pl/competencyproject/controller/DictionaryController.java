package pl.competencyproject.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.io.FileNotFoundException;
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
    private Button addNewDictionaryButton;
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
    public void addDictionary() throws FileNotFoundException {
        CSVReader csvReader=CSVReader.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
        csvReader.chooseLevel(nameOfLevelChoiceBox.getSelectionModel().toString(),nameOfCategoryChoiceBox.getSelectionModel().toString());

        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv Files","*.csv"));

        List<File> selectedFiles=fileChooser.showOpenMultipleDialog(null);
        if(selectedFiles!=null){
            for(int i=0;i<selectedFiles.size();i++) {
                selectedFiles.get(i).getAbsolutePath();
                csvReader.chooseCSV(selectedFiles.get(i));
            }

        }
    }
    @FXML
    public void clearChoice(){
        typeOfDictionaryChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(null);
        nameOfCategoryChoiceBox.setDisable(true);
        addNewDictionaryButton.setDisable(true);
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
        nameOfCategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldValue,newValue)->addNewDictionaryButton.setDisable(false));
    }
}
