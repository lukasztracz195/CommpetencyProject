package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.csv.FileOfCSV;
import pl.competencyproject.model.csv.LibraryCSV;
import pl.competencyproject.model.dao.classes.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReaderCSVTest {

    private CSVReader csvReader = CSVReader.getInstance();
    private static ManageDictionaryWords MDW;
    private FileOfCSV fileOfCSV;
    private LibraryCSV libraryCSV;

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addFamily() throws FileNotFoundException {
        SessionLogon.IdLoggedUser = 1;
        ManageLevels ML = ManageLevels.getTestInstance();
        ManageDictionaryWords MDW = ManageDictionaryWords.getTestInstance();
        ML.addLevel("B2", "Working life");
        int id = ML.existLevel("B2", "Working life");
        Assertions.assertEquals(1, id);
        csvReader.chooseCSV("FAMILY",true);
        csvReader.chooseLevel("B2", "Working life");
        int insertet = csvReader.insertFamily(true);
        int insertet2 = MDW.getDictionaryByFamilie(1).size();
        Assertions.assertEquals(6, insertet2);
    }

    @Test
    public void LCSTest() {
        String source = "employ";
        String consider = "employable";
        String result = csvReader.LCS(source, consider);
        Assertions.assertEquals("employ", result);
    }

    @Test
    public void LevensteinTest() {
        String source = "employ";
        String consider = "employable";
        int result = csvReader.levenstein(source, consider);
        Assertions.assertEquals(4, result);
    }

    @Test
    public void isInFamilyTest() {
        String source = "employ";
        String consider = "to employ";
        boolean result = csvReader.isItFamily(source, consider);
        Assertions.assertTrue(result);
    }

    @Test
    public void selectLongestWord() {
        String stringWithSpace = "gsdggggdhd hdfhdhdhfs gggshhs adgfagdg aggaggg aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String result = csvReader.selectlongestWord(stringWithSpace);
        Assertions.assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", result);
    }

    @Test
    public void findHeadFamilyTest() {
        List<String> list = new ArrayList<>();
        list.add("employ");
        list.add("employment");
        list.add("unemployment");
        list.add("employee");
        list.add("employer");
        list.add("employable");
        String head = csvReader.findHeadFamily(list);
        Assertions.assertEquals("employ", head);
    }

    @Test
    public void insertDictionarySentencesTest() {
        String sentencePL="Idź za głosem serca – ale rozum zabieraj ze sobą.";
        String sentenceENG = "Follow your heart, but take your brain with you.";

        ManageLevels ML = ManageLevels.getTestInstance();
        int idLevel = ML.existLevel("B2", "Working life");
        if(idLevel == -1){
            ML.addLevel("B2", "Working life");
        }
        try {
             csvReader.chooseCSV("DICTIONARY_SENTENCES_INSERT",true);
             csvReader.chooseLevel("B2", "Working life");
             int result = csvReader.insertDictionarySentences(true);
             Assertions.assertNotSame(0,result);
             ManageDictionarySentences MDS = ManageDictionarySentences.getTestInstance();
             idLevel = ML.existLevel("B2", "Working life");
             int exist = MDS.existDictionarySentences(idLevel, sentenceENG, sentencePL);
             Assertions.assertNotSame(-1,exist);
             } catch (FileNotFoundException e) {
             e.printStackTrace();
        }
    }

    @Test
    public void insertDictionaryWordswithoutFamilyTest(){
        String sentencePL="pies";
        String sentenceENG = "dog";

        ManageLevels ML = ManageLevels.getTestInstance();
        ManageWordsENG MWE = ManageWordsENG.getTestInstance();
        ManageWordsPL MWP = ManageWordsPL.getTestInstance();

        int idLevel = ML.existLevel("B2", "Working life");
        if(idLevel == -1){
            ML.addLevel("B2", "Working life");
        }
        try {
            csvReader.chooseCSV("DICTIONARY_WORDS_INSERT",true);
            csvReader.chooseLevel("B2", "Working life");
            int result = csvReader.insertDictionaryWordswithoutFamily(true);
            Assertions.assertNotSame(0,result);
            ManageDictionaryWords MDW = ManageDictionaryWords.getTestInstance();
            idLevel = ML.existLevel("B2", "Working life");
            int idWordENG = MWE.existWordENG(sentenceENG);
            int idWordPL = MWP.existWordPL(sentencePL);
            int exist = MDW.existDictionaryWordsWithoutFamilie(idLevel, idWordENG, idWordPL);
            Assertions.assertNotSame(-1,exist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



}