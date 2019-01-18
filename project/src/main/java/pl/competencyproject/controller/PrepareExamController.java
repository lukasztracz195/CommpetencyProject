package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.multithreadProcessing.ThreadForDownloadData;
import pl.competencyproject.model.dao.classes.ManageFamily;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.pollingMechanizm.DictionaryMap;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.competencyproject.model.enums.TypeOfDictionaryDownloaded.*;
import static pl.competencyproject.model.enums.TypeOfDictionaryLanguage.ENGtoPL;
import static pl.competencyproject.model.enums.TypeOfDictionaryLanguage.PLtoENG;

public class PrepareExamController extends AbstractController implements Initializable {

    @FXML
    private ChoiceBox<TypeOfDictionaryLanguage> languagesOrderChoiceBox;
    @FXML
    private ChoiceBox<TypeOfDictionaryDownloaded> typeOfDictionaryChoiceBox;
    @FXML
    private ChoiceBox<String> nameOfLevelChoiceBox;
    @FXML
    private ChoiceBox<String> nameOfCategoryChoiceBox;
    @FXML
    private ChoiceBox<String> headsOfFamilyChoiceBox;
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
    @FXML
    private Label feedbackLabel;

    private boolean finisheSent = false;
    private DictionaryMap dictionaryMap;
    private ManageLevels ML;
    private ManageFamily MF;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ML = new ManageLevels(TypeOfUsedDatabase.OnlineOrginalDatabase);
        MF = new ManageFamily(TypeOfUsedDatabase.OnlineOrginalDatabase);
        super.setClockDate(clockLabel, dateLabel);
        languagesOrderChoiceBox.getItems().addAll(PLtoENG, ENGtoPL);

        languagesOrderChoiceBox.setOnAction(e -> {
            feedbackLabel.setVisible(false);
            if (!languagesOrderChoiceBox.getSelectionModel().isEmpty()) {
                if (typeOfDictionaryChoiceBox.getValue() == null) {
                    typeOfDictionaryChoiceBox.getItems().addAll(DictionaryOfWords, DictionaryOfFamilys, DictionaryOfSentences);
                }
                typeOfDictionaryChoiceBox.setDisable(false);
            } else {
                typeOfDictionaryChoiceBox.setValue(null);
                typeOfDictionaryChoiceBox.setDisable(true);
            }
        });

        typeOfDictionaryChoiceBox.setOnAction(e -> {
            feedbackLabel.setVisible(false);
            if (typeOfDictionaryChoiceBox.getValue().equals(DictionaryOfFamilys)) {
                headsOfFamilyChoiceBox.setDisable(false);
            } else {
                headsOfFamilyChoiceBox.setValue(null);
                headsOfFamilyChoiceBox.setDisable(true);
            }
            if (!typeOfDictionaryChoiceBox.getSelectionModel().isEmpty()) {
                nameOfLevelChoiceBox.getItems().addAll(ML.getNamesLevels());
                nameOfLevelChoiceBox.setDisable(false);
            } else {

                nameOfLevelChoiceBox.setDisable(true);
            }
        });
        nameOfLevelChoiceBox.setOnAction(e -> {
            feedbackLabel.setVisible(false);
            if (!nameOfLevelChoiceBox.getSelectionModel().isEmpty()) {
                nameOfCategoryChoiceBox.getItems().addAll(ML.getCategories(nameOfLevelChoiceBox.getValue()));
                nameOfCategoryChoiceBox.setDisable(false);
            } else {
                nameOfLevelChoiceBox.setValue(null);
                nameOfCategoryChoiceBox.setDisable(true);
            }


        });

        nameOfCategoryChoiceBox.setOnAction(e -> {
            feedbackLabel.setVisible(false);
            if (!nameOfLevelChoiceBox.getSelectionModel().isEmpty()) {
                if (typeOfDictionaryChoiceBox.getValue().equals(DictionaryOfFamilys)) {
                    headsOfFamilyChoiceBox.getItems().addAll(MF.getHeadOfFamilys(ML.existLevel(nameOfLevelChoiceBox.getValue(), nameOfCategoryChoiceBox.getSelectionModel().getSelectedItem())));
                }
                if (!(languagesOrderChoiceBox.getSelectionModel().isEmpty() && typeOfDictionaryChoiceBox.getSelectionModel().isEmpty() && nameOfLevelChoiceBox.getSelectionModel().isEmpty())) {
                    loadDictionaryButton.setDisable(false);
                } else {
                    loadDictionaryButton.setDisable(true);
                }
            }


        });

        headsOfFamilyChoiceBox.setOnAction(e -> {
            feedbackLabel.setVisible(false);
            if (!headsOfFamilyChoiceBox.getSelectionModel().isEmpty()) {
                if (!(languagesOrderChoiceBox.getSelectionModel().isEmpty() && typeOfDictionaryChoiceBox.getSelectionModel().isEmpty() && nameOfLevelChoiceBox.getSelectionModel().isEmpty())) {
                    loadDictionaryButton.setDisable(false);
                } else {
                    loadDictionaryButton.setDisable(true);
                }
            }
        });
    }

    @FXML
    public void loadDictionaries() {
        if (dictionaryMap != null) {
            dictionaryMap.hardReset();
        }
        dictionaryMap = DictionaryMap.getInstance();
        loadDictionaryButton.setDisable(true);
        dictionaryMap.setTypeDB(TypeOfUsedDatabase.OnlineOrginalDatabase);
        switch (typeOfDictionaryChoiceBox.getValue()) {
            case DictionaryOfWords: {
                dictionaryMap.setDictionaryOfWords(nameOfLevelChoiceBox.getValue(), nameOfCategoryChoiceBox.getValue());
                break;
            }
            case DictionaryOfFamilys: {
                dictionaryMap.setDictionaryOfFamily(headsOfFamilyChoiceBox.getValue());
                break;
            }
            case DictionaryOfSentences: {
                dictionaryMap.setDictionaryOfSentences(nameOfLevelChoiceBox.getValue(), nameOfCategoryChoiceBox.getValue());
                break;
            }
        }
        dictionaryMap.setTypeLangToLang(TypeOfDictionaryLanguage.valueOf(languagesOrderChoiceBox.getValue().toString()));
        progressBar.setVisible(true);
        int howMatch = dictionaryMap.getNumberOfRecordsToDownload();

        if (howMatch > 0) {
            feedbackLabel.setVisible(false);
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.execute(new ThreadForDownloadData());
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    progressBar.setProgress(dictionaryMap.getDownloadedRecords() / (double) howMatch);
                    if (dictionaryMap.getDownloadedRecords() == howMatch) {
                        if (!es.isShutdown()) {
                            es.shutdown();
                        }
                        progressBar.setVisible(false);
                        startExamButton.setDisable(false);
                        timer.cancel();
                    }
                }
            };
            timer.schedule(task, 0, 1000);
        } else {
            feedbackLabel.setVisible(true);
            feedbackLabel.setText("This Dictionary haven't content yet. We are Sorry!");
        }
    }


    @FXML
    public void startingExam() {
        FXMLLoader loader = MainController.createLoader(MainController.Exam, this);
        Pane pane = MainController.createPane(loader);
        ExamController controller = loader.getController();
        controller.setMainController(mainController);
        mainController.setScreen(pane);
    }

    @FXML
    public void clearChoice() {

        languagesOrderChoiceBox.setDisable(false);

        typeOfDictionaryChoiceBox.setDisable(true);
        typeOfDictionaryChoiceBox.setValue(null);

        nameOfLevelChoiceBox.setDisable(true);
        nameOfLevelChoiceBox.setValue(null);

        nameOfCategoryChoiceBox.setDisable(true);
        nameOfCategoryChoiceBox.setValue(null);

        headsOfFamilyChoiceBox.setDisable(true);
        headsOfFamilyChoiceBox.setValue(null);


        startExamButton.setDisable(true);
        loadDictionaryButton.setDisable(true);
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
}
