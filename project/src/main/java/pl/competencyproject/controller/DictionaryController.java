package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.FileChooser;
import pl.competencyproject.model.ClassesToRunnable.ThreadForDownloadData;
import pl.competencyproject.model.ClassesToRunnable.ThreadForSentData;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.dao.classes.ManageFamily;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.*;

public class DictionaryController extends AbstractController implements Initializable {

    @FXML
    private ChoiceBox<TypeOfDictionaryDownloaded> typeOfDictionaryChoiceBox;
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
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label returnLabel;

    private boolean finisheSent = false;

    @FXML
    private Label feedbackLabel;

    private ManageLevels ML = new ManageLevels(TypeOfUsedDatabase.OnlineTestDatabase);
    private ManageFamily MF = new ManageFamily(TypeOfUsedDatabase.OnlineTestDatabase);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        setChoiceBoxOfTypesOfDictionary();
        enableChoiceBoxes();
    }

    @FXML
    public void addDictionary() throws FileNotFoundException {
        returnLabel.setVisible(true);
        CSVReader csvReader = CSVReader.getInstance(TypeOfUsedDatabase.OnlineTestDatabase);
        csvReader.chooseLevel(nameOfLevelChoiceBox.getSelectionModel().getSelectedItem().toString(), nameOfCategoryChoiceBox.getSelectionModel().getSelectedItem().toString());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv Files", "*.csv"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (int i = 0; i < selectedFiles.size(); i++) {
                selectedFiles.get(i).getAbsolutePath();
                csvReader.chooseCSV(selectedFiles.get(i));
            }
            csvReader.setTypeDictionary(DictionaryOfWords);
            int howMatch = csvReader.getNumberOfLinesToSendTODB();
            feedbackLabel.setVisible(true);
            progressBar.setVisible(true);
            progressBar.setProgress(0);
            feedbackLabel.setText("Adding to Database file with "+howMatch+" lines");
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(new ThreadForSentData(TypeOfUsedDatabase.OnlineOrginalDatabase));
            addNewDictionaryButton.setDisable(true);
            Timer timer = new Timer("Timer");
            TimerTask task = new TimerTask() {
                public void run() {
                        progressBar.setProgress(csvReader.getValueProgress() / (double) howMatch);
                    if(csvReader.getTransferCommplete()) {
                        progressBar.setVisible(false);
                        timer.cancel();
                    }
                }
            };
            timer.schedule(task,0,1000);
            disableChoiceBoxes();

        }
    }

    @FXML
    public void clearChoice() {
        typeOfDictionaryChoiceBox.setValue(NONE);
        nameOfLevelChoiceBox.setValue(NONE);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(NONE);
        nameOfCategoryChoiceBox.setDisable(true);
        addNewDictionaryButton.setDisable(true);
        feedbackLabel.setVisible(false);
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

    private void setChoiceBoxOfTypesOfDictionary() {

        typeOfDictionaryChoiceBox.getItems().add(DictionaryOfWords);
        nameOfLevelChoiceBox.getItems().add("B2");
        nameOfCategoryChoiceBox.getItems().addAll(ML.getCategories("B2"));
    }

    private void disableChoiceBoxes(){
        typeOfDictionaryChoiceBox.setDisable(true);
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setDisable(true);
    }


    private void enableChoiceBoxes() {

        typeOfDictionaryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> nameOfLevelChoiceBox.setDisable(false));
        nameOfLevelChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> nameOfCategoryChoiceBox.setDisable(false));
        nameOfCategoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> addNewDictionaryButton.setDisable(false));


    }
}
