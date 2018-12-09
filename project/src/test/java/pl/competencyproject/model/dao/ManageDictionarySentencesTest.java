package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.ManageDictionarySentences;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.entities.Dictionary_Sentence;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public class ManageDictionarySentencesTest {
    private ManageDictionarySentences MDS;
    private ManageLevels ML;
    private int idLevel = -1;
    private String sentenceENG = "I am a human";
    private String sentencePL = "Jestem czlowiekiem";
    int idDictionary = -1;

    @Before
    public void init() {
        SessionLogon.IdLoggedUser = 1;
        MDS = ManageDictionarySentences.getInstance(TypeOfUsedDatabase.OfflineTestDataBase);
        ML = ManageLevels.getInstance(TypeOfUsedDatabase.OfflineTestDataBase);
        if (ML.existLevel("B2", "TestLevel") == -1) {
            idLevel = ML.addLevel("B2", "TestLevel");
        }
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void DictionarySentecesTest() {
        idDictionary = MDS.insertDictionarySentece(idLevel, sentenceENG, sentencePL);
        int exist = MDS.existDictionarySentences(idLevel, sentenceENG, sentencePL);
        Assertions.assertEquals(exist, idDictionary);

        Dictionary_Sentence dicSen = MDS.getDictionary_Sentences(exist);
        Assertions.assertNotNull(dicSen);
        Assertions.assertEquals("I am a human", dicSen.getSentencesENG());
        Assertions.assertEquals("Jestem czlowiekiem", dicSen.getSentencesPL());
    }




}
