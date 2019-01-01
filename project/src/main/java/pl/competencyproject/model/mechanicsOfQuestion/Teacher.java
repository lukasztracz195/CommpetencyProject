
package pl.competencyproject.model.mechanicsOfQuestion;

import lombok.Getter;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.interfaces.ITeacher;

import java.util.*;

@Getter
public class Teacher implements ITeacher {
    public static Teacher instance;

    private SortedMap<Word, List<String>> currentMapQuestion;
    private DictionaryMap factoryDictionary;
    private TypeOfUsedDatabase type;
    private String currentQuestion;
    private Word key;
    private List<String> currentAnswer;
    private Integer currentRound = 0;
    private Integer trades = 0;
    private Integer numberOfGoodAnswers = 0;
    private Integer numberOfBadAnswers = 0;
    private Double valueProgress = (double) 0;
    private int numberMaxOfSessions;

    public Teacher(TypeOfUsedDatabase type) {
        this.type = type;
    }

    public void initDictionary(String nameLevel, String nameCategorie, TypeOfDictionaryDownloaded typeDictionary, TypeOfDictionaryLanguage typeLanguage) {
        factoryDictionary = DictionaryMap.getInstance();
        int check = getIdOfLevel(nameLevel, nameCategorie);
        if (check != -1) {
            factoryDictionary.loadDictionary(check, typeDictionary, typeLanguage, type);
            currentMapQuestion = factoryDictionary.getRandTenMap();
            numberMaxOfSessions = factoryDictionary.calculateTheNumberOfCombinations();
            changeQuestion(0);
        } else {
            System.out.println("Nie istnieje taki level");
        }
    }

    private Integer getIdOfLevel(String nameLevel, String nameCategorie) {
        ManageLevels ML = new ManageLevels(type);
        int result = ML.existLevel(nameLevel, nameCategorie);
        return result;
    }

    public boolean checkAnswer(String answer, int delayInMilisecundes) {
        if (!currentMapQuestion.isEmpty()) {
            List<Integer> diffrentsAnswers = new ArrayList<>();
            trades++;
            //0 == good  1 == bad
            int size = currentAnswer.size();
            System.out.println("Value: " + currentAnswer.toString());
            for (String s : currentAnswer) {
                diffrentsAnswers.add(CSVReader.levenstein(answer, s));
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


    private void goodAnswer(int delayInMilisecundes) {
        currentMapQuestion.remove(key, currentAnswer);
        changeQuestion(delayInMilisecundes);
        numberOfGoodAnswers++;
    }

    private void badAnswer(int delayInMilisecundes) {
        Word oldKey = new Word(key);
        List<String> tmpValue = new ArrayList<>(currentAnswer);
        getCurrentMapQuestion().remove(key, tmpValue);
        oldKey.decreasNumberOfAttempts();
        getCurrentMapQuestion().put(oldKey, tmpValue);
        if (oldKey.getNumberOfTries() == 0) {
            getCurrentMapQuestion().remove(oldKey, tmpValue);
        }
        changeQuestion(delayInMilisecundes);
        numberOfBadAnswers++;
    }

    private void changeQuestion(int delayInMilisecundes) {
        try {

            if (delayInMilisecundes > 0) {
                Thread.sleep(delayInMilisecundes);
            }
            if (!currentMapQuestion.isEmpty()) {
                key = currentMapQuestion.lastKey();
                currentQuestion = key.toString();
                currentAnswer = currentMapQuestion.get(key);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initNextRoundOfQuestions() {
        if (getFactoryDictionary().getCollectionOfuniqueness().size() < getFactoryDictionary().getSizeOfFullMap()) {
            getValueProgress();
            currentMapQuestion = factoryDictionary.getRandTenMap();
            currentRound++;
        }
    }

    public Double getValueProgress() {
        int fullSizeOfDDictionary = factoryDictionary.getSizeOfFullMap();
        double valuePart1 = (10.0 * currentRound) / fullSizeOfDDictionary;
        double valuePart2 = ((numberOfGoodAnswers * 1.5) - numberOfBadAnswers) / trades;
        valueProgress += valuePart1 * valuePart2;
        return valueProgress;
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


}

