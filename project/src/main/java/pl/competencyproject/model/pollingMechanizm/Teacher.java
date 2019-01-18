
package pl.competencyproject.model.pollingMechanizm;


import lombok.Getter;
import pl.competencyproject.model.csv.CSVReader;


import pl.competencyproject.model.dao.classes.ManageStats;

import pl.competencyproject.model.dao.classes.ManageLevels;

import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.ManageDictionaryWords;
import pl.competencyproject.model.dao.classes.ManageWordsENG;
import pl.competencyproject.model.dao.classes.ManageWordsPL;

import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.pollingMechanizm.interfaces.ITeacher;

import java.util.*;

@Getter
public class Teacher implements ITeacher {
    public static Teacher instance;

    private SortedMap<Word, List<String>> currentMapQuestion;
    private DictionaryMap factoryDictionary;
    private String currentQuestion;
    private Word key;
    private List<String> currentAnswer;
    private int fullSizeOfDictionary;
    private int currentRound = 1;
    private int sizeCurrentMapQuestionOnStart;
    private int numberOfGoodAnswers = 0;
    private int numberOfWrongAnswers = 0;
    private int totalNumberOfGoodAnswers = 0;
    private int totalNumberOfWrongAnswers = 0;
    private double totalValueProgress = 0.0;
    private double valueProgress = 0.0;
    private int numberMaxOfSessions;
    private int nummerQuestion = 0;
    private int numberAllQuestions;

    public Teacher() {

    }

    public void setDictionary(DictionaryMap dictionary) {
        factoryDictionary = dictionary;
        currentMapQuestion = factoryDictionary.getRandTenMap();
        sizeCurrentMapQuestionOnStart = currentMapQuestion.size();
        numberAllQuestions = sizeCurrentMapQuestionOnStart;
        nummerQuestion++;
        fullSizeOfDictionary = factoryDictionary.getSizeOfFullMap();
        numberMaxOfSessions = factoryDictionary.calculateTheNumberOfCombinations();
        changeQuestion(0);
    }

    public boolean checkAnswer(String answer, int delayInMilisecundes) {
        if (!currentMapQuestion.isEmpty()) {
            List<Integer> diffrentsAnswers = new ArrayList<>();
            //0 == good  1 == bad
            int size = currentAnswer.size();
            for (String s : currentAnswer) {
                diffrentsAnswers.add(CSVReader.levenstein(answer.toLowerCase(), s.toLowerCase()));
            }
            Collections.sort(diffrentsAnswers);
            int checked = diffrentsAnswers.get(0);
            if (checked <= 1) {
                goodAnswer(delayInMilisecundes);
                return true;

            } else {
                badAnswer(delayInMilisecundes);
                return false;
            }
        }
        return false;
    }


    public void goodAnswer(int delayInMilisecundes) {
        numberOfGoodAnswers++;
        nummerQuestion++;
        calculateTotalProgress(true);
        currentMapQuestion.remove(key, currentAnswer);
        changeQuestion(delayInMilisecundes);

    }

    public void answerWithoutPoints(int delayInMilisecundes){
        calculateTotalProgress(true);
        numberAllQuestions--;
        currentMapQuestion.remove(key, currentAnswer);
        changeQuestion(delayInMilisecundes);
    }

    private void badAnswer(int delayInMilisecundes) {
        if (key.getNumberOfTries() == 0) {
            calculateTotalProgress(false);
            numberAllQuestions--;
            currentMapQuestion.remove(key, currentAnswer);
        } else {
            Word oldKey = new Word(key);
            List<String> tmpValue = new ArrayList<>(currentAnswer);
            getCurrentMapQuestion().remove(key, tmpValue);
            oldKey.decreasNumberOfAttempts();
            getCurrentMapQuestion().put(oldKey, tmpValue);
            if (oldKey.getNumberOfTries() == 0) {
                getCurrentMapQuestion().remove(oldKey, tmpValue);
            }
            numberOfWrongAnswers++;
            calcuateProgresValue();
        }
        changeQuestion(delayInMilisecundes);
    }

    private void changeQuestion(int delayInMilisecundes) {
        try {

            if (delayInMilisecundes > 0) {
                Thread.sleep(delayInMilisecundes);
            }
            if (!currentMapQuestion.isEmpty()) {
                key = currentMapQuestion.lastKey();
                currentQuestion = key.getWord();
                currentAnswer = currentMapQuestion.get(key);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initNextRoundOfQuestions() {
        if (currentRound <= numberMaxOfSessions) {
            currentMapQuestion = factoryDictionary.getRandTenMap();
            sizeCurrentMapQuestionOnStart = currentMapQuestion.size();
            numberAllQuestions = sizeCurrentMapQuestionOnStart;
            nummerQuestion = 0;
            currentRound++;
            resetGoodAndWrongsAnswers();
            valueProgress = 0.0;
        }
    }

    public String randGoodOrBadAnswer() {
        Random random = new Random();
        // 0 = bad     1 = good
        int choice = random.nextInt(100);
        String result = null;
        if (choice <= 50) {
            result = "abcdfghijkl";
        } else {
            int choice2 = random.nextInt(currentAnswer.size());
            result = currentAnswer.get(choice2);
        }

        return result;
    }

    public void resetTotalProgress() {
        totalNumberOfWrongAnswers = 0;
        totalNumberOfGoodAnswers = 0;
        totalValueProgress = 0.0;
    }

    public void resetGoodAndWrongsAnswers() {
        numberOfGoodAnswers = 0;
        numberOfWrongAnswers = 0;
    }

    private void resetProgress(){
        valueProgress = 0.0;
    }

    private double calculateProgressForGoodAnswer() {
        double tmpProggres = (100.0 / fullSizeOfDictionary) / 3;
        int factor = key.getNumberOfTries();
        tmpProggres = tmpProggres * factor;
        return tmpProggres;
    }

    private double calculateProgressForBadAnswer( ) {
        return (100.0 / fullSizeOfDictionary);
    }

    private void calculateTotalProgress(boolean goodAnswer) {
        if (goodAnswer) {
            valueProgress = numberOfGoodAnswers*1.0/(numberOfWrongAnswers + sizeCurrentMapQuestionOnStart);
            if(numberMaxOfSessions > 1) {
                totalValueProgress += calculateProgressForGoodAnswer();
            }else{
                totalValueProgress = valueProgress;
            }
            totalNumberOfGoodAnswers += numberOfGoodAnswers;

        } else {
            valueProgress = numberOfGoodAnswers*1.0/(numberOfWrongAnswers + sizeCurrentMapQuestionOnStart);
            totalValueProgress -= calculateProgressForBadAnswer();
            totalNumberOfWrongAnswers += numberOfWrongAnswers;
        }
    }

    private void calcuateProgresValue(){
        valueProgress = numberOfGoodAnswers*1.0/(numberOfWrongAnswers + sizeCurrentMapQuestionOnStart);
    }

    public void saveProgressToDB(){
        ManageStats MS = new ManageStats(TypeOfUsedDatabase.OnlineOrginalDatabase);
        MS.addStat(this.factoryDictionary.getIdLevel(),round(totalValueProgress,2));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

