package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageWordsPL;
import pl.competencyproject.model.entities.Word_PL;

public class ManageWordsPLTest {
    private ManageWordsPL manageWordsPL = ManageWordsPL.getTestInstance();

    private String word = "wybuch";

    @Test
    public void addTest(){
        int idWord = manageWordsPL.addWordPL(word);
        int result = manageWordsPL.existWordPL(word);
        Assertions.assertEquals(idWord,result);
    }

    @Test
    public void existTest(){
        int result = manageWordsPL.existWordPL(word);
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void deleteTest(){
        manageWordsPL.deleteWordPL(manageWordsPL.existWordPL(word));
        int result = manageWordsPL.existWordPL(word);
        Assertions.assertEquals(-1,result);
    }

    @Test
    public void getTest(){
        int idWord = manageWordsPL.existWordPL(word);
        Word_PL wordPL = manageWordsPL.getWordPL(idWord);
        wordPL.equals(word);
        System.out.println(wordPL);
    }
}

