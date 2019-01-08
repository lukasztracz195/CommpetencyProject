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

public class ExamController extends AbstractController implements Initializable{

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

    private Teacher teacher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        teacher = new Teacher();
        teacher.setDictionary(DictionaryMap.getInstance());
        questionLabel.setText(teacher.getCurrentQuestion());
    }

    @FXML
    public void showingAnswer(){
        hiddenAnswerLabel.setVisible(true);

/*
        for (int i = 0; i < answersList.size(); i++) {
            answer=answersList.get(i).toString();
            changedAnswer+=answer+","+" ";
        }
        */
        hiddenAnswerLabel.setText(teacher.getCurrentAnswer().toString());

    }

    @FXML
    public void checkingAnswer(){
        teacher.checkAnswer(odpowiedz.getText(),0);
        questionLabel.setText(teacher.getCurrentQuestion());
        correctAnswerLabel.setText("Corrects: "+teacher.getNumberOfGoodAnswers());
        wrongAnswerLabel.setText("Wrongs: "+teacher.getNumberOfWrongAnswers());
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
}
