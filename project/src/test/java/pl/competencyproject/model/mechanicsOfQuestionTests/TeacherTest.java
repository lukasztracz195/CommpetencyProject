package pl.competencyproject.model.mechanicsOfQuestionTests;
/*
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;
import pl.competencyproject.model.mechanicsOfQuestion.Teacher;
import pl.competencyproject.model.mechanicsOfQuestion.Word;

import java.util.List;
import java.util.Map;

public class TeacherTest {

    private static Teacher teacher;
    private static TypeOfUsedDatabase type = TypeOfUsedDatabase.OfflineTestDataBase;
    private static ManageLevels ML;
    private static CSVReader csv;

    @Before
    public void init(){
        SessionLogon.IdLoggedUser = 1;
        csv = CSVReader.getInstance(type);
        ML = ManageLevels.getInstance(type);
       int idLevel =  ML.addLevel("B2","test");

        csv.chooseCSV("StructureOfTheUniversity");
        csv.chooseLevel("B2","test");
        csv.insertDictionaryWordswithoutFamily();

        DictionaryMap map = teacher.getFactoryDictionary();
        System.out.println("Keys");
        Map<Integer, Word> keys = map.getKeysAllMap();
        for (Map.Entry<Integer, Word> entry : keys.entrySet()) {
            Integer key = entry.getKey();
            Word value = entry.getValue();
            System.out.println(key.toString() + ": " + value.toString());
        }
        System.out.println("Values");
        Map<String, List<String>> values = map.getDictionary();
        for (Map.Entry<String, List<String>> entry : values.entrySet()) {
            String key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println(key.toString() + ": " + value.toString());
        }
        System.out.println("Dictionary");
        Map<Word, List<String>> dictionary = map.getRandTenMap();
        for (Map.Entry<Word, List<String>> entry : dictionary.entrySet()) {
            Word key = entry.getKey();
            List<String> value = entry.getValue();
            System.out.println(key.toString() + ": " + value.toString());
        }
    }

    @Test
    public void TeachingTest(){
        teacher = Teacher.getInstance(type);
        teacher.initDictionary("B2","test", TypeOfDictionaryDownloaded.DictionaryOfWords, TypeOfDictionaryLanguage.ENGtoPL);

        while(!teacher.getCurrentMapQuestion().isEmpty()){
            System.out.println("Question: "+teacher.getCurrentQuestion());
            String answer = teacher.randGoodOrBadAnswer();
            System.out.println("Ansswer: "+answer);
        }
        Assertions.assertNotNull(teacher.getNumberOfGoodAnswers());
        Assertions.assertNotNull(teacher.getNumberOfBadAnswers());
        Assertions.assertNotNull(teacher.getValueProgress());
        System.out.println("Good Answers: "+teacher.getNumberOfGoodAnswers());
        System.out.println("Wrong Answers: "+teacher.getNumberOfBadAnswers());
        System.out.println("ValueProgress: "+teacher.getValueProgress());
    }
    @After
    public void end(){
        SessionLogon.IdLoggedUser = -1;
    }
}
*/