package pl.competencyproject.model.DAO;

import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageFamily;
import pl.competencyproject.model.DAO.classes.ManageLevels;
import pl.competencyproject.model.DAO.classes.ManageUsers;
import pl.competencyproject.model.entities.Family;


public class ManageFamilyTest {

    private ManageFamily manageFamily = ManageFamily.getTestInstance();
    private ManageLevels manageLevels = ManageLevels.getTestInstance();
    private ManageUsers manageUsers = ManageUsers.getTestInstance();

    private String nameLevel = "B2";
    private String nameCategorie = "Working life auuuuu";
    private String headFamily = "Employee12";
    private String email = "testUser1@gmail.com";
    private String password = "qwerty3";

    private int IdLevel = 0;
    private int IdFamily = 0;
    private int IdFamily2 = 0;

    @Test
    public void addTest(){
        boolean loggedTest1 = false;
        if(manageUsers.existUser(email) == -1){
            manageUsers.addUser(email,password);
            loggedTest1 = true;
            manageUsers.updateActiveUser(manageUsers.existUser(email), loggedTest1);
        }
        else{
            loggedTest1 = true;
            manageUsers.updateActiveUser(manageUsers.existUser(email), loggedTest1);
        }
        IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        System.out.println(IdLevel);
        IdFamily = manageFamily.addFamily(IdLevel,headFamily);
        System.out.println(IdFamily);
        IdFamily2 = manageFamily.existFamily(IdLevel,headFamily);
        Assertions.assertEquals(IdFamily,IdFamily2);

    }

    @Test
    public void existTest(){
        IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        IdFamily = manageFamily.existFamily(IdLevel,headFamily);
        if(IdFamily == -1) throw new RuntimeException("Error");
    }

    @Test
    public void getFamilyTest(){
        IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        IdFamily = manageFamily.existFamily(IdLevel,headFamily);
        Family family = manageFamily.getFamili(IdFamily);
        Assertions.assertEquals(headFamily,family);
    }

    @Test
    public void deleteTest(){
        IdLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        IdFamily = manageFamily.existFamily(IdLevel,headFamily);
        manageFamily.deleteFamily(IdFamily);
        int result = manageFamily.existFamily(IdLevel,headFamily);
        if(result == -1) throw new RuntimeException("Error");
    }
}
