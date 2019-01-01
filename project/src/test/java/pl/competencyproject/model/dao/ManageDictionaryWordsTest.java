package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageDictionaryWordsTest {

    private static ManageDictionaryWords MDW;
    private static ManageLevels ML;
    private static ManageFamily MF;
    private static ManageWordsPL MWP;
    private static ManageWordsENG MWE;
    private static int idMDW;
    private static int idML;
    private static int idMF;
    private static int idMWP;
    private static int idMWE;
    private static String nameLevel = "B2";
    private static String nameCategory = "Work life";
    private static String wordPL = "dom";
    private static String wordENG = "house";
    private static int added =0;

    @Before
    public void init() {
        SessionLogon.IdLoggedUser = 1;
        MDW = new ManageDictionaryWords(TypeOfUsedDatabase.OfflineTestDataBase);
        ML = new ManageLevels(TypeOfUsedDatabase.OfflineTestDataBase);
        MF = new ManageFamily(TypeOfUsedDatabase.OfflineTestDataBase);
        MWP = new ManageWordsPL(TypeOfUsedDatabase.OfflineTestDataBase);
        MWE = new ManageWordsENG(TypeOfUsedDatabase.OfflineTestDataBase);
        idML = ML.existLevel(nameLevel, nameCategory);
        if (idML == -1) {
            idML = ML.addLevel(nameLevel, nameCategory);
        }
        idMF = MF.existFamily(idML, wordENG);
        if (idMF == -1) {
            idMF = MF.addFamily(idML, wordENG);
        }

        idMWE = MWE.existWordENG(wordENG);
        if (idMWE == -1) {
            MWE.addWordENG(wordENG);
        }

        idMWP = MWP.existWordPL(wordPL);
        if (idMWP == -1) {
            idMWP = MWP.addWordPL(wordPL);
        }

    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    } // wylogowanie usera

    @Test
    public void insertDictionaryWord() {
        idMDW = MDW.existDictionaryWords(idML, idMF, idMWE, idMWP);
        System.out.println(idMDW);
        if (idMDW == -1) {
            added++;
            idMDW = MDW.insertDictionaryWords(idML, idMF, idMWE, idMWP);
        }
        int exist = MDW.existDictionaryWords(idML, idMF, idMWE, idMWP);
        Assertions.assertEquals(idMDW, exist);
    }

    @Test
    public void getDictionaryByLevel(){
        idMDW = MDW.existDictionaryWords(idML, idMF, idMWE, idMWP);
        if (idMDW <= 0) {
            added++;
            idMDW = MDW.insertDictionaryWords(idML, idMF, idMWE, idMWP);
        }
        List<Dictionary_Word> list = MDW.getDictionaryByLevel(idML);
        int size = list.size();
        Assertions.assertEquals(added,size);

    }

    @Test
    public void getDictionaryByFamily(){
        idMDW = MDW.existDictionaryWords(idML, idMF, idMWE, idMWP);
        if (idMDW <= 0) {
            added++;
            idMDW = MDW.insertDictionaryWords(idML, idMF, idMWE, idMWP);
        }
        List<Dictionary_Word> list = MDW.getDictionaryByFamilie(idMF);
        System.out.println(list);
        int size = list.size();
        Assertions.assertEquals(added,size);
    }

    @Test
    public void insertDictionaryWordsWithoutFamiliy(){
        int notFamily = MDW.insertDictionaryWordswithoutFamilie(idML, idMWE, idMWP);
        int exist =  MDW.existDictionaryWordsWithoutFamilie(idML,idMWE,idMWP);
        Assertions.assertEquals(notFamily,exist);
    }


}
