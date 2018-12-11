package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.ManageWordsENG;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public class ManageWordsENGTest {

    private static ManageWordsENG MWE;
    private static String word = "explosion";
    private static int idWE;
    private static TypeOfUsedDatabase type = TypeOfUsedDatabase.OfflineTestDataBase;


    @Before
    public void init() {
        MWE = ManageWordsENG.getInstance(type);
        SessionLogon.IdLoggedUser = 1;
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addTest() {
         idWE = MWE.existWordENG(word);
        if( idWE == -1) {
            idWE = MWE.addWordENG(word);
        }
         int result = MWE.existWordENG(word);
        Assertions.assertEquals(idWE, result);
    }

    @Test
    public void getTest() {
        idWE = MWE.existWordENG(word);
        if (idWE == -1) idWE = MWE.addWordENG(word);
        Word_ENG wordENG = MWE.getWordENG(idWE);
        Assertions.assertEquals(word, wordENG.getWordENG());
    }

}
