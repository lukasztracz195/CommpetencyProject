package pl.competencyproject.model.DAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import pl.competencyproject.model.DAO.classes.ManageLevels;
import pl.competencyproject.model.entities.Level;

import java.util.List;

public class ManageLevelsTest {

    private ManageLevels manageLevels;
    private String nameLevel = "B2";
    private String nameCategorie = "Working life";
    private String email = "testUser3@gmail.com";
    private String password = "qwerty3";
    private int IdLevel = 0;

    @Before
    public void init() {
        manageLevels = ManageLevels.getTestInstance();
        SessionLogon.IdLoggedUser = 1;
    }

    @After
    public void end() {
        SessionLogon.IdLoggedUser = -1;
    }

    @Test
    public void addTest() {
        IdLevel = manageLevels.addLevel(nameLevel, nameCategorie);
        int result = manageLevels.existLevel(nameLevel, nameCategorie);
        Assertions.assertEquals(IdLevel, result);
    }

    @Test
    public void getTest() {
        Level a = manageLevels.getLevel(manageLevels.existLevel(nameLevel, nameCategorie));
        a.equals(null);
    }

    @Test
    public void deleteTest() {
        IdLevel = manageLevels.existLevel(nameLevel, nameCategorie);
        manageLevels.deleteLevel(IdLevel);
        int result = manageLevels.existLevel(nameLevel, nameCategorie);
        Assertions.assertEquals(-1, result);
    }

    @Test
    public void getAllTest() {
        List list = manageLevels.getAllLevels();
        list.equals(null);
    }

    @Test
    public void getCategoryTest() {
        List list = manageLevels.getCategories(nameCategorie);
        list.equals(nameCategorie);
    }

    @Test
    public void getNameTest() {
        List list = manageLevels.getNamesLevels();
        list.equals(null);
    }
}
