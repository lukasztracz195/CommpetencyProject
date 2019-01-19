package pl.competencyproject.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import pl.competencyproject.model.pollingMechanizm.DictionaryMap;
import pl.competencyproject.model.pollingMechanizm.Teacher;

import java.net.URL;
import java.util.Optional;
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
    @FXML
    private Label counterQuestionLabel;
    private Teacher teacher;
    private String lastAnswer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.setClockDate(clockLabel, dateLabel);
        teacher = new Teacher();
        teacher.setDictionary(DictionaryMap.getInstance());
        questionLabel.setText(teacher.getCurrentQuestion());
        lastAnswer = teacher.getCurrentAnswer().toString();
        setExamInformation();
        setProgressValueInfo();
    }

    @FXML
    public void showingAnswer() {
        hiddenAnswerLabel.setVisible(true);
        hiddenAnswerLabel.setTextFill(new Color(0, 0, 0, 1));
        hiddenAnswerLabel.setText(teacher.getCurrentAnswer().toString());
        teacher.answerWithoutPoints(0);
        setExamInformation();
        setProgressValueInfo();
        questionLabel.setText(teacher.getCurrentQuestion());
        odpowiedz.clear();
        correctAnswerLabel.setText("Corrects: " + teacher.getNumberOfGoodAnswers());
        wrongAnswerLabel.setText("Wrongs: " + teacher.getNumberOfWrongAnswers());
    }

    @FXML
    public void checkingAnswer() {
        lastAnswer = teacher.getCurrentAnswer().toString();
        boolean good = teacher.checkAnswer(odpowiedz.getText(), 0);
        if (!good) {
            if (!lastAnswer.equals(teacher.getCurrentAnswer().toString()))
                hiddenAnswerLabel.setVisible(true);
            setInformationAboutCorrectAnswer();
        }
        setExamInformation();
        setProgressValueInfo();
        odpowiedz.clear();
        questionLabel.setText(teacher.getCurrentQuestion());
        correctAnswerLabel.setText("Corrects: " + teacher.getNumberOfGoodAnswers());
        wrongAnswerLabel.setText("Wrongs: " + teacher.getNumberOfWrongAnswers());
        openDecisionWindow();
    }


    @FXML
    public void back() {
        teacher.saveProgressToDB();
        teacher.getFactoryDictionary().hardReset();
        super.back(mainController, this);
    }

    @FXML
    public void logout() {
        teacher.saveProgressToDB();
        mainController.loadLogonScreen();
        sessionLogon.logOut();
    }

    private void openDecisionWindow() {

        if (teacher.getCurrentMapQuestion().isEmpty()) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Test");
            confirmation.setContentText("Czy chcesz kontynuować test?");
            confirmation.setHeaderText("Potwierdzenie");
            Optional<ButtonType> action = confirmation.showAndWait();
            if (action.get() == ButtonType.OK) {
                if (teacher.getNumberMaxOfSessions() == 1) {
                    teacher.getFactoryDictionary().lightReset();
                } else {
                    teacher.initNextRoundOfQuestions();
                }
                hideLabel();
                questionLabel.setText(teacher.getCurrentQuestion());
                lastAnswer = teacher.getCurrentAnswer().toString();
                setProgressValueInfo();
                setInformationAboutNumbersOfQuestion();
                setExamInformation();
                correctAnswerLabel.setText("Corrects: 0");
                wrongAnswerLabel.setText("Wrongs: 0");
            } else {
                teacher.saveProgressToDB();
                teacher.getFactoryDictionary().hardReset();
                super.back(mainController, this);
            }
        }
    }

    private void setInformationAboutNumbersOfQuestion() {
        if(teacher.getNummerQuestion() <= 10) {
            String sb = (teacher.getNummerQuestion()) +
                    " / " +
                    teacher.getNumberAllQuestions();
            counterQuestionLabel.setText(sb);
        }
    }

    private void setExamInformation() {
        setInformationAboutNumbersOfQuestion();
        StringBuilder sb = new StringBuilder();
        DictionaryMap dm = teacher.getFactoryDictionary();
        int sizeFullMap = dm.getSizeOfFullMap();
        int used = dm.getCollectionOfuniqueness().size();
        int rest = sizeFullMap - used;
        sb.append("Session ")
                .append(teacher.getCurrentRound())
                .append(" from ")
                .append(teacher.getNumberMaxOfSessions())
                .append(" remained ")
                .append(rest)
                .append(" questions");
        examInformationLabel.setText(sb.toString());
    }

    private void setProgressValueInfo() {
        double totalValueProgressRounded = teacher.getTotalValueProgress();
        double currentValueProgressRounded = teacher.getValueProgress();

        if (teacher.getNumberMaxOfSessions() == 1) {
            totalValueProgressLabel.setText((teacher.round(totalValueProgressRounded * 100, 2)) + "%");
        } else {
            totalValueProgressLabel.setText((teacher.round(totalValueProgressRounded, 2)) + "%");
        }

        currentValueProgressLabel.setText((teacher.round(currentValueProgressRounded * 100, 2)) + "%");
    }

    private void setInformationAboutCorrectAnswer() {
        hiddenAnswerLabel.setVisible(true);
        hiddenAnswerLabel.setTextFill(new Color(1, 0, 0, 1));
        hiddenAnswerLabel.setText("Correct was:" + lastAnswer);
    }

    @FXML
    public void checkPressEnter(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER")) {
            checkingAnswer();
        }
    }

    @FXML
    public void hideLabel() {
        hiddenAnswerLabel.setVisible(false);
    }
}
