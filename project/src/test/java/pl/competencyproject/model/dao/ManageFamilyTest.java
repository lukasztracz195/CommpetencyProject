package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.ManageFamily;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.entities.Family;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public class ManageFamilyTest {

    private static ManageFamily MF;
    private static ManageLevels ML;
    private static int idLevel;
    private static int idFamily;
    private static String nameLEvel = "B2";
    private static String category = "Work life";
    private static String head = "headFamily";

    @Before
    public void init() {
        SessionLogon.IdLoggedUser = 1;
        MF = ManageFamily.getInstance(TypeOfUsedDatabase.OfflineTestDataBase);
        ML = ManageLevels.getInstance(TypeOfUsedDatabase.OfflineTestDataBase);
        idLevel = ML.existLevel(nameLEvel, category);
        if (idLevel <= 0) {
            idLevel = ML.addLevel(nameLEvel, category);
        }
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void insertFamiy() {
        idFamily = MF.addFamily(idLevel, head);
        int exist = MF.existFamily(idLevel, head);
        Assertions.assertEquals(idFamily, exist);
    }

    @Test
    public void getFamily() {
        idFamily = MF.existFamily(idLevel, head);
        if (idFamily == -1) {
            idFamily = MF.addFamily(idLevel, head);
        }
        Family family = MF.getFamily(idFamily);
        Assertions.assertNotNull(family);
        int checkIdLevel = family.getIdLevel();
        String checkHead = family.getHeadFamily();
        Assertions.assertEquals(idLevel, checkIdLevel);
        Assertions.assertEquals(head, checkHead);
    }

    @Test
    public void deleteFamily() {
        idFamily = MF.existFamily(idLevel, head);
        if (MF.existFamily(idLevel, head) == -1) {
            idFamily = MF.addFamily(idLevel, head);
        } else {

        }
        MF.deleteFamily(idFamily);
        int notExist = MF.existFamily(idLevel, head);
        Assertions.assertEquals(-1, notExist);
    }
}
