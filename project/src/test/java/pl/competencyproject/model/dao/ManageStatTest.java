package pl.competencyproject.model.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.dao.classes.ManageStats;
import pl.competencyproject.model.dao.classes.ManageUsers;
import pl.competencyproject.model.entities.Stat;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.Date;

public class ManageStatTest {

    private  static Double valProgress = 75.4;
    private  static Double valProgress1 = 22.1;
    private  static ManageStats MS;
    private  static ManageUsers MU;
    private  static ManageLevels ML;
    private  static String nameLevel = "B2";
    private  static String nameCategorie = "Working";
    private  static String email = "testUser3@gmail.com";
    private  static String password = "qwerty3";
    private  static int idStat ;
    private  static int idUser;
    private  static int idLevel;
    private  static TypeOfUsedDatabase type = TypeOfUsedDatabase.OfflineTestDataBase;

    @BeforeClass
    public void init() {
        MS = new ManageStats(type);
        MU = new ManageUsers(type);
        ML = new ManageLevels(type);
        if (MU.existUser(email) == -1 || idUser == -1) {
            idUser = MU.addUser(email, password);
            SessionLogon.IdLoggedUser = idUser;
        }
        if (ML.existLevel(nameLevel, nameCategorie) == -1 || idLevel == -1) {
            idLevel = ML.addLevel(nameLevel, nameCategorie);
        }
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addTest() {
        idStat = MS.existStat(1);
        if(idStat == -1) {
            idStat = MS.addStat(idLevel, valProgress);
        }
        int exist = MS.existStat(idStat);
        System.out.println(idStat+" "+exist);
        Assertions.assertEquals(idStat, exist);
        Stat stat = MS.getStat(idStat);
        Assertions.assertNotNull(stat);
    }

    @Test
    public void getTest() {
        Stat stat = MS.getStat(idStat);
        Assertions.assertNotNull(stat);
        int idU = stat.getIdUser();
        int idL = stat.getIdLevel();
        Date date = stat.getDateInput();
        Double value = stat.getValueProgress();
        System.out.println("idUser: " + idUser + " idLevel: " + idLevel);
        Assertions.assertEquals(idUser, idU);
        Assertions.assertEquals(idLevel, idL);
        Assertions.assertTrue(new Date().after(date));
        Double calculated = (valProgress / valProgress1) + (valProgress1 * 2);
        Assertions.assertEquals(calculated, value);
    }

    @Test
    public void updateTest() {
        MS.updateStat(idStat, valProgress1);
        Stat a = MS.getStat(idStat);
        Assertions.assertNotNull(a);
        Double changed = a.getValueProgress();
        Double calculated = (valProgress / valProgress1) + (valProgress1 * 2);
        Assertions.assertEquals(calculated, changed);
    }

    @Test
    public void deleteTest() {

        int id = MS.existStat(idStat);
        if (id == -1) {
            idStat = MS.addStat(idLevel, valProgress);
            id = MS.existStat(idStat);
        }
        Assertions.assertEquals(idStat, id);
        MS.deleteStat(id);
        int result = MS.existStat(id);
        Assertions.assertEquals(-1, result);
    }


}
