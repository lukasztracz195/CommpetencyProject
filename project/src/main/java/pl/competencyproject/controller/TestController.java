package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import pl.competencyproject.model.dao.classes.ManageFamily;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.DictionaryOfFamilys;
import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.DictionaryOfWords;

public class TestController extends AbstractController implements Initializable {

    @FXML
    private ChoiceBox typeOfDictionaryChoiceBox;
    @FXML
    private ChoiceBox nameOfLevelChoiceBox;
    @FXML
    private ChoiceBox nameOfCategoryChoiceBox;
    @FXML
    private ChoiceBox headsOfFamilyChoiceBox;
    @FXML
    private Button startExamButton;
    @FXML
    private Button loadDictionaryButton;
    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label returnLabel;

    private boolean finisheSent = false;

    @FXML
    private Label feedbackLabel;

    private ManageLevels ML = new ManageLevels(TypeOfUsedDatabase.OnlineOrginalDatabase);
    private ManageFamily MF = new ManageFamily(TypeOfUsedDatabase.OnlineOrginalDatabase);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        setChoiceBoxOfTypesOfDictionary();
        enableChoiceBoxes();
    }

    @FXML
    public void loadDictionaries(){
        DictionaryMap dictionaryMap=DictionaryMap.getInstance();
        
        dictionaryMap.setDictionaryOfWords(nameOfLevelChoiceBox.getValue().toString(),nameOfCategoryChoiceBox.getValue().toString());
        dictionaryMap.setDictionaryOfFamily(headsOfFamilyChoiceBox.getValue().toString());
    }
    @FXML
    public void startingExam(){
        FXMLLoader loader = MainController.createLoader(MainController.Exam, this);
        Pane pane = MainController.createPane(loader);
        ExamController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);

        disableChoiceBoxes();
    }
    @FXML
    public void clearChoice() {
        typeOfDictionaryChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setValue(null);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(null);
        nameOfCategoryChoiceBox.setDisable(true);
        headsOfFamilyChoiceBox.setValue(null);
        headsOfFamilyChoiceBox.setDisable(true);
        startExamButton.setDisable(true);
        loadDictionaryButton.setDisable(true);
        feedbackLabel.setVisible(false);
    }
    private void setChoiceBoxOfTypesOfDictionary() {

        typeOfDictionaryChoiceBox.getItems().add(DictionaryOfWords.toString());
        nameOfLevelChoiceBox.getItems().add("B2");
        nameOfCategoryChoiceBox.getItems().addAll(ML.getCategories("B2"));
        headsOfFamilyChoiceBox.getItems().add(DictionaryOfFamilys.toString());
    }
    private void disableChoiceBoxes(){
        typeOfDictionaryChoiceBox.setDisable(true);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setDisable(true);
        headsOfFamilyChoiceBox.setDisable(true);
    }


    private void enableChoiceBoxes() {

        typeOfDictionaryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> nameOfLevelChoiceBox.setDisable(false));
        nameOfLevelChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> nameOfCategoryChoiceBox.setDisable(false));
        nameOfCategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> headsOfFamilyChoiceBox.setDisable(false));
        headsOfFamilyChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->startExamButton.setDisable(false));
        headsOfFamilyChoiceBox.getSelectionModel().selectedItemProperty().addListener((v,oldvalue,newvalue)->loadDictionaryButton.setDisable(false));
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
