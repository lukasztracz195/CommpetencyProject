package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.ManageWordsENG;
import pl.competencyproject.model.entities.Word_ENG;

public class ManageWordsENGTest {

    private static ManageWordsENG MWE;
    private static String word = "explosion";
    private static int idWE;



    @Before
    public void init() {
        MWE = ManageWordsENG.getTestInstance();
        SessionLogon.IdLoggedUser = 1;
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addTest() {
        idWE = MWE.addWordENG(word);
        int result = MWE.existWordENG(word);
        Assertions.assertEquals(idWE, result);
    }

    @Test
    public void deleteTest() {
        idWE = MWE.existWordENG(word);
        if (idWE == -1) idWE = MWE.addWordENG(word);
        MWE.deleteWordENG(idWE);
        int result = MWE.existWordENG(word);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void getTest() {
        idWE = MWE.existWordENG(word);
        if (idWE == -1) idWE = MWE.addWordENG(word);
        Word_ENG wordENG = MWE.getWordENG(idWE);
        Assertions.assertEquals(word, wordENG.getWordENG());
    }

}
