package pl.competencyproject.model.DAO;
/*

import org.junit.Before;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.connection.SessionFactoryConfig;

class ManageUsersTest {

   String email = "testUser1@gmail.com";
   String emailChange="testUser2@gmail.com";
   String password = "qwerty1";
   String passwordChanged="qwerty2";
   ManageUsers manageUsers = ManageUsers.getInstance();
    int id = -1;
    boolean loggedTest1 = true;
    boolean loggedTest2 = false;

    @Test
    public void addTest(){
        int id = ManageUsers.getInstance().addUser(email,password);
       Assertions.assertEquals(id,manageUsers.existUser(email));

    }

    @Test
    public void updateEmailTest(){
        manageUsers.updateEmail(manageUsers.existUser(email),emailChange);
        manageUsers.checkUserEmail(manageUsers.existUser(emailChange),emailChange);

    }

    @Test
    public void updatePasswordTest(){
        manageUsers.updatePasswordUser(manageUsers.existUser(emailChange),passwordChanged);
        manageUsers.checkUserPassword(manageUsers.existUser(emailChange),passwordChanged);

    }

    @Test
    public void loggerEnableTest(){
        manageUsers.updateActiveUser(manageUsers.existUser(emailChange),loggedTest1);
        manageUsers.checkLogedUser(manageUsers.existUser(emailChange));
    }

    @Test
    public void loggerDisableTest(){
        manageUsers.updateActiveUser(manageUsers.existUser(emailChange),loggedTest2);
        manageUsers.checkLogedUser(manageUsers.existUser(emailChange));
    }
    @Test
    public void deleteTest(){
        manageUsers.deleteUser(manageUsers.existUser(emailChange));
        Assertions.assertEquals(-1,manageUsers.existUser(emailChange));
    }


 }
 */