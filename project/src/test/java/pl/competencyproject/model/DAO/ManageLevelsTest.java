package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageLevels;
import pl.competencyproject.model.entities.Level;

import java.util.List;


class ManageLevelsTest {

    private ManageLevels manageLevels = ManageLevels.getTestInstance();

    private String nameLevel = "B2";
    private String nameCategorie = "Working life auuuuu";
    private String email = "testUser3@gmail.com";
    private String password = "qwerty3";
    private int IdLevel = 0;


    @Test
    public void addTest(){
        IdLevel = manageLevels.addLevel(nameLevel,nameCategorie);
        int result = manageLevels.existLevel(nameLevel,nameCategorie);
        Assertions.assertEquals(IdLevel,result);
    }

    @Test
    public void getTest(){
        Level a = manageLevels.getLevel(manageLevels.existLevel(nameLevel,nameCategorie ));
        a.equals(null);
    }

    @Test
    public void existTest(){
        int result = manageLevels.existLevel(nameLevel,nameCategorie );
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void deleteTest(){
        IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        manageLevels.deleteLevel(IdLevel);
        int result = manageLevels.existLevel(nameLevel,nameCategorie);
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void getAllTest(){
        List list = manageLevels.getAllLevels();
        list.equals(null);
    }

    @Test
    public void getCategoryTest(){
        List list = manageLevels.getCategories(nameCategorie);
        list.equals(nameCategorie);
    }

    @Test
    public void getNameTest(){
        List list = manageLevels.getNamesLevels();
        list.equals(null);
    }

}
