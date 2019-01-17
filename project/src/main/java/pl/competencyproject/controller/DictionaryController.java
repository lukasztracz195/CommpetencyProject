package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.multithreadProcessing.ThreadForSentData;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.DictionaryOfWords;
import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.NONE;

public class DictionaryController extends AbstractController implements Initializable {

    @FXML
    private ChoiceBox<TypeOfDictionaryDownloaded> typeOfDictionaryChoiceBox;
    @FXML
    private ChoiceBox <String> nameOfLevelChoiceBox;
    @FXML
    private ChoiceBox <String> nameOfCategoryChoiceBox;
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
    @FXML
    private Label feedbackLabel;
    private TypeOfUsedDatabase usedDatabase = TypeOfUsedDatabase.OnlineOrginalDatabase;



    private ManageLevels ML = new ManageLevels(usedDatabase);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        setChoiceBoxOfTypesOfDictionary();
        enableChoiceBoxes();
    }

    @FXML
    public void addDictionary(){

        CSVReader csvReader = CSVReader.getInstance(usedDatabase);
        csvReader.chooseLevel(nameOfLevelChoiceBox.getSelectionModel().getSelectedItem(), nameOfCategoryChoiceBox.getSelectionModel().getSelectedItem());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv Files", "*.csv"));

        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            for (File selectedFile : selectedFiles) {

                csvReader.chooseCSV(selectedFile);
            }
            csvReader.setTypeDictionary(DictionaryOfWords);
            if(csvReader.checkHeaderWordsSentenses(csvReader.getHeader())) {
                returnLabel.setVisible(true);
                int howManyRecords = csvReader.getNumberOfLinesToSendTODB();
                feedbackLabel.setVisible(true);
                feedbackLabel.setTextFill(new Color(0, 0, 0, 1));
                progressBar.setVisible(true);
                progressBar.setProgress(0);
                feedbackLabel.setText("Adding to Database file with " + howManyRecords + " lines");
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.execute(new ThreadForSentData(usedDatabase));
                addNewDictionaryButton.setDisable(true);
                Timer timer = new Timer("Timer");
                TimerTask task = new TimerTask() {
                    public void run() {
                        System.out.println(csvReader.getValueProgress()+" "+howManyRecords);
                        progressBar.setProgress(csvReader.getValueProgress() / (double) howManyRecords);
                        if (csvReader.getTransferCommplete()) {
                            progressBar.setVisible(false);
                           if(!es.isShutdown()){
                               es.shutdown();
                           }
                            timer.cancel();
                        }
                    }
                };
                timer.schedule(task, 0, 1000);
                disableChoiceBoxes();
            }else{
                feedbackLabel.setVisible(true);
                feedbackLabel.setTextFill(new Color(1, 0, 0, 1));
                feedbackLabel.setText("Wrong header in your file \""+csvReader.getHeader()+"\"");
            }

        }
    }

    @FXML
    public void clearChoice() {
        typeOfDictionaryChoiceBox.setValue(NONE);
        nameOfLevelChoiceBox.setValue(NONE.toString());
        nameOfLevelChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(NONE.toString());
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
