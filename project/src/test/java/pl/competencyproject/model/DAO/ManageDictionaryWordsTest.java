package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.*;

import java.util.List;

public class ManageDictionaryWordsTest {
    private ManageLevels manageLevels = ManageLevels.getTestInstance();
    private ManageFamily manageFamily = ManageFamily.getTestInstance();
    private ManageWordsENG manageWordsENG = ManageWordsENG.getTestInstance();
    private ManageWordsPL manageWordsPL = ManageWordsPL.getTestInstance();
    private ManageDictionaryWords manageDictionaryWords = ManageDictionaryWords.getTestInstance();

    private String nameLevel = "B2";
    private String nameCategorie = "Working life auuuuu";
    private String headFamily = "Employee";
    private String wordPL = "wybuch";
    private String wordENG = "explosion";

    private int IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
    private int IdFamily = manageFamily.existFamily(IdLevel,headFamily);
    private int IdWordENG = manageWordsPL.existWordPL(wordPL);
    private int IdWordPL = manageWordsENG.existWordENG(wordENG);

    int idDictionary = 0;

    @Test
    public void insertDictionaryWordsTest(){
        idDictionary = manageDictionaryWords.insertDictionaryWords(IdLevel,IdFamily,IdWordENG,IdWordPL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    public void insertDictionaryWordswithoutFamilieTest(){
        idDictionary = manageDictionaryWords.insertDictionaryWordswithoutFamilie(IdLevel,IdWordENG,IdWordPL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    public void insertDictionaryWordswithoutLevelTest(){
        idDictionary = manageDictionaryWords.insertDictionaryWordswithoutLevel(IdFamily,IdWordENG,IdWordPL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    public void getDictionaryByLevelTest(){
        List list = manageDictionaryWords.getDictionaryByLevel(IdLevel);
        list.equals(null);
    }

    @Test
    public void getDictionaryByFamilieTest() {
        List list = manageDictionaryWords.getDictionaryByLevel(IdFamily);
        list.equals(null);
    }

    @Test
    public void existDictionaryWordsTest() {
        Integer idDictionary = manageDictionaryWords.existDictionaryWords(IdLevel,IdFamily,IdWordENG,IdWordPL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    public void existDictionaryWordsWithoutFamilieTest() {
        Integer idDictionary = manageDictionaryWords.existDictionaryWordsWithoutFamilie(IdLevel,IdWordENG,IdWordPL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

}
