package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.competencyproject.model.DAO.classes.ManageWordsENG;
import pl.competencyproject.model.entities.Word_ENG;


public class ManageWordsENGTest {
    private ManageWordsENG manageWordsENG = ManageWordsENG.getTestInstance();

    private String word = "explosion";


    @Test
    public void addTest(){
        int idWord = manageWordsENG.addWordENG(word);
        int result = manageWordsENG.existWordENG(word);
        Assertions.assertEquals(idWord,result);
    }

    @Test
    public void existTest(){
        int result = manageWordsENG.existWordENG(word);
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void deleteTest(){
        manageWordsENG.deleteWordENG(manageWordsENG.existWordENG(word));
        int result = manageWordsENG.existWordENG(word);
        Assertions.assertEquals(-1,result);
    }

    @Test
    public void getTest(){
        int idWord = manageWordsENG.existWordENG(word);
        Word_ENG wordENG = manageWordsENG.getWordENG(idWord);
        wordENG.equals(word);
    }
}
