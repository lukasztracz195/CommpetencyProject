package pl.competencyproject.model.DAO;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.entities.User;
import sun.nio.cs.ext.MacArabic;

import static junit.framework.TestCase.assertSame;
import static org.mockito.Mockito.mock;

class ManageUsersTest {

   String email = "testuser@gmail.com";
   String password = "qwerty1";
   String passwordChanged="qwerty2";

   int id = -1;
   boolean logged = true;
    @Before
    public void create(){
        SessionFactoryConfig.getSessionFactory();

    }

    @Test
    public void addTest(){
        int id = ManageUsers.addUser(email,password);
       Assertions.assertEquals(id,ManageUsers.existUser(email));

    }

    @Test
    public void updateTest(){
        ManageUsers.updatePasswordUser(ManageUsers.existUser(email),passwordChanged);
        ManageUsers.checkUserPassword(ManageUsers.existUser(email),passwordChanged);

    }

    @Test
    public void loggerTest(){
        ManageUsers.updateActiveUser(ManageUsers.existUser(email),logged);
        ManageUsers.checkLogedUser(ManageUsers.existUser(email));
    }

    @Test
    public void deleteTest(){
        ManageUsers.deleteUser(ManageUsers.existUser(email));
        Assertions.assertEquals(-1,ManageUsers.existUser(email));
    }


 }