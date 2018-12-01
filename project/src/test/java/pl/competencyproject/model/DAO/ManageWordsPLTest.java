package pl.competencyproject.model.DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.DAO.classes.ManageWordsPL;
import pl.competencyproject.model.entities.Word_PL;

public class ManageWordsPLTest {

    private static ManageWordsPL MWP;
    private static String word = "test";
    private static int idWE;

    @Before
    public void init() {
        MWP = ManageWordsPL.getTestInstance();
        SessionLogon.IdLoggedUser = 1;
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addTest() {
        idWE = MWP.addWordPL(word);
        int result = MWP.existWordPL(word);
        Assertions.assertEquals(idWE, result);
    }

    @Test
    public void deleteTest() {
        idWE = MWP.existWordPL(word);
        if (idWE == -1) idWE = MWP.addWordPL(word);
        MWP.deleteWordPL(idWE);
        int result = MWP.existWordPL(word);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void getTest() {
        idWE = MWP.existWordPL(word);
        if (idWE == -1) idWE = MWP.addWordPL(word);
        Word_PL wordPL = MWP.getWordPL(idWE);
        Assertions.assertEquals(word, wordPL.getWordPL());
    }

}
