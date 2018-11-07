package pl.competencyproject.model.DAO;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.connection.SessionFactoryConfig;

class ManageUsersTest {

   String email = "testUser1@gmail.com";
   String emailChange="testUser2@gmail.com";
   String password = "qwerty1";
   String passwordChanged="qwerty2";

    int id = -1;
    boolean loggedTest1 = true;
    boolean loggedTest2 = false;
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
    public void updateEmailTest(){
        ManageUsers.updateEmail(ManageUsers.existUser(email),emailChange);
        ManageUsers.checkUserEmail(ManageUsers.existUser(emailChange),emailChange);

    }

    @Test
    public void updatePasswordTest(){
        ManageUsers.updatePasswordUser(ManageUsers.existUser(emailChange),passwordChanged);
        ManageUsers.checkUserPassword(ManageUsers.existUser(emailChange),passwordChanged);

    }

    @Test
    public void loggerEnableTest(){
        ManageUsers.updateActiveUser(ManageUsers.existUser(emailChange),loggedTest1);
        ManageUsers.checkLogedUser(ManageUsers.existUser(emailChange));
    }

    @Test
    public void loggerDisableTest(){
        ManageUsers.updateActiveUser(ManageUsers.existUser(emailChange),loggedTest2);
        ManageUsers.checkLogedUser(ManageUsers.existUser(emailChange));
    }
    @Test
    public void deleteTest(){
        ManageUsers.deleteUser(ManageUsers.existUser(emailChange));
        Assertions.assertEquals(-1,ManageUsers.existUser(emailChange));
    }


 }