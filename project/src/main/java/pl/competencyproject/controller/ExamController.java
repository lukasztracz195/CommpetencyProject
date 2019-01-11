package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;
import pl.competencyproject.model.mechanicsOfQuestion.Teacher;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExamController extends AbstractController implements Initializable {

    @FXML
    private Label hiddenAnswerLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField odpowiedz;
    @FXML
    private Label correctAnswerLabel;
    @FXML
    private Label wrongAnswerLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label examInformationLabel;
    @FXML
    private Label totalValueProgressLabel;
    @FXML
    private Label currentValueProgressLabel;

    private Teacher teacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        teacher = new Teacher();
        teacher.setDictionary(DictionaryMap.getInstance());
        questionLabel.setText(teacher.getCurrentQuestion());
        setExamInformation();
        setProgressValueInfo();
    }

    @FXML
    public void showingAnswer() throws InterruptedException {
        hiddenAnswerLabel.setVisible(true);
        setExamInformation();
        setProgressValueInfo();
        hiddenAnswerLabel.setText(teacher.getCurrentAnswer().toString());
        teacher.checkAnswer("abcdefghij", 5);
        questionLabel.setText(teacher.getCurrentQuestion());
        odpowiedz.clear();
        correctAnswerLabel.setText("Corrects: " + teacher.getNumberOfGoodAnswers());
        wrongAnswerLabel.setText("Wrongs: " + teacher.getNumberOfWrongAnswers());

    }

    @FXML
    public void checkingAnswer() {
        setExamInformation();
        setProgressValueInfo();
        teacher.checkAnswer(odpowiedz.getText(), 0);
        odpowiedz.clear();
        questionLabel.setText(teacher.getCurrentQuestion());
        correctAnswerLabel.setText("Corrects: " + teacher.getNumberOfGoodAnswers());
        wrongAnswerLabel.setText("Wrongs: " + teacher.getNumberOfWrongAnswers());
        hiddenAnswerLabel.setVisible(false);
    }


    @FXML
    public void back() {
        teacher.getFactoryDictionary().hardReset();
        super.back(mainController, this);
    }

    @FXML
    public void logout() {
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }

    public void setExamInformation() {
        StringBuilder sb = new StringBuilder();
        DictionaryMap dic = teacher.getFactoryDictionary();
        int sizeFullMap = dic.getSizeOfFullMap();
        int used = dic.getCollectionOfuniqueness().size();
        int rest = sizeFullMap - used;
        sb.append("Session ")
                .append(teacher.getCurrentRound())
                .append(" ")
                .append(teacher.getNumberMaxOfSessions())
                .append(" the were ")
                .append(rest)
                .append(" questions");
        examInformationLabel.setText(sb.toString());
    }

    public void setProgressValueInfo() {
        totalValueProgressLabel.setText(String.valueOf(teacher.getTotalValueProgress()));
        currentValueProgressLabel.setText(String.valueOf(teacher.getValueProgress()));    }
}
