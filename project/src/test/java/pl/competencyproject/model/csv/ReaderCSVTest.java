package pl.competencyproject.model.csv;

import org.junit.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.csv.CSVReader;
import pl.competencyproject.model.csv.FileOfCSV;
import pl.competencyproject.model.csv.LibraryCSV;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ReaderCSVTest {

    private TypeOfUsedDatabase typeDB = TypeOfUsedDatabase.OfflineTestDataBase;
    private CSVReader csvReader;
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
        csvReader = CSVReader.getInstance(typeDB);
        ManageLevels ML = new ManageLevels(TypeOfUsedDatabase.OfflineTestDataBase);
        ManageDictionaryWords MDW = new ManageDictionaryWords(TypeOfUsedDatabase.OfflineTestDataBase);
        ML.addLevel("B2", "Working life");
        int id = ML.existLevel("B2", "Working life");
        Assertions.assertEquals(1, id);
        boolean ist = csvReader.chooseCSV("FAMILY");
        Assertions.assertTrue(ist);
        csvReader.chooseLevel("B2", "Working life");
        csvReader.setTypeDictionary(TypeOfDictionaryDownloaded.DictionaryOfFamilys);
        int insertet = csvReader.initSentToDB();
        int insertet2 = MDW.getDictionaryByFamilie(1).size();
        Assertions.assertEquals(6, insertet2);
        csvReader.destroy();
    }

    @Test
    public void LCSTest() {
        csvReader = CSVReader.getInstance(typeDB);
        String source = "employ";
        String consider = "employable";
        String result = csvReader.LCS(source, consider);
        Assertions.assertEquals("employ", result);
        csvReader.destroy();
    }

    @Test
    public void LevensteinTest() {
        csvReader = CSVReader.getInstance(typeDB);
        String source = "employ";
        String consider = "employable";
        int result = csvReader.levenstein(source, consider);
        Assertions.assertEquals(4, result);
        csvReader.destroy();
    }

    @Test
    public void isInFamilyTest() {
        csvReader = CSVReader.getInstance(typeDB);
        String source = "employ";
        String consider = "to employ";
        boolean result = csvReader.isItFamily(source, consider);
        Assertions.assertTrue(result);
        csvReader.destroy();
    }

    @Test
    public void selectLongestWord() {
        csvReader = CSVReader.getInstance(typeDB);
        String stringWithSpace = "gsdggggdhd hdfhdhdhfs gggshhs adgfagdg aggaggg aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String result = csvReader.selectlongestWord(stringWithSpace);
        Assertions.assertEquals("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", result);
        csvReader.destroy();
    }

    @Test
    public void findHeadFamilyTest() {
        csvReader = CSVReader.getInstance(typeDB);
        List<String> list = new ArrayList<>();
        list.add("employ");
        list.add("employment");
        list.add("unemployment");
        list.add("employee");
        list.add("employer");
        list.add("employable");
        String head = csvReader.findHeadFamily(list);
        Assertions.assertEquals("employ", head);
        csvReader.destroy();
    }

    @Test
    public void insertDictionarySentencesTest() {
        csvReader = CSVReader.getInstance(typeDB);
        String sentencePL = "Idź za głosem serca – ale rozum zabieraj ze sobą.";
        String sentenceENG = "Follow your heart, but take your brain with you.";

        ManageLevels ML = new ManageLevels(TypeOfUsedDatabase.OfflineTestDataBase);
        int idLevel = ML.existLevel("B2", "Working life");
        if (idLevel == -1) {
            ML.addLevel("B2", "Working life");
        }
        csvReader.chooseCSV("DICTIONARY_SENTENCES_INSERT");
        csvReader.chooseLevel("B2", "Working life");
        csvReader.setTypeDictionary(TypeOfDictionaryDownloaded.DictionaryOfSentences);
        int result = csvReader.initSentToDB();
        Assertions.assertNotSame(0, result);
        ManageDictionarySentences MDS = new ManageDictionarySentences(TypeOfUsedDatabase.OfflineTestDataBase);
        idLevel = ML.existLevel("B2", "Working life");
        int exist = MDS.existDictionarySentences(idLevel, sentenceENG, sentencePL);
        Assertions.assertNotSame(-1, exist);
        csvReader.destroy();
    }

    @Test
    public void insertDictionaryWordswithoutFamilyTest() {
        csvReader = CSVReader.getInstance(typeDB);
        String sentencePL = "pies";
        String sentenceENG = "dog";

        ManageLevels ML = new ManageLevels(TypeOfUsedDatabase.OfflineTestDataBase);
        ManageWordsENG MWE = new ManageWordsENG(TypeOfUsedDatabase.OfflineTestDataBase);
        ManageWordsPL MWP = new ManageWordsPL(TypeOfUsedDatabase.OfflineTestDataBase);

        int idLevel = ML.existLevel("B2", "Working life");
        if (idLevel == -1) {
            ML.addLevel("B2", "Working life");
        }
        csvReader.chooseCSV("DICTIONARY_WORDS_INSERT");
        csvReader.chooseLevel("B2", "Working life");
        csvReader.setTypeDictionary(TypeOfDictionaryDownloaded.DictionaryOfWords);
        int result = csvReader.initSentToDB();
        Assertions.assertNotSame(0, result);
        ManageDictionaryWords MDW = new ManageDictionaryWords(TypeOfUsedDatabase.OfflineTestDataBase);
        idLevel = ML.existLevel("B2", "Working life");
        int idWordENG = MWE.existWordENG(sentenceENG);
        int idWordPL = MWP.existWordPL(sentencePL);
        int exist = MDW.existDictionaryWordsWithoutFamilie(idLevel, idWordENG, idWordPL);
        Assertions.assertNotSame(-1, exist);
        csvReader.destroy();
    }


}
