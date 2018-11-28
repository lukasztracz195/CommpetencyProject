package pl.competencyproject.model.DAO;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageUsers;

class ManageUsersTest {

    private String email = "testUser1@gmail.com";
    private String emailChange = "testUser2@gmail.com";
    private ManageUsers manageUsers = ManageUsers.getTestInstance();

    int id = -1;

    @Test
    public void addTest() {
        String password = "qwerty1";
        int id = ManageUsers.getInstance().addUser(email, password);
        Assertions.assertEquals(id, manageUsers.existUser(email));
    }

    @Test
    public void updateEmailTest() {
        manageUsers.updateEmail(manageUsers.existUser(email), emailChange);
        manageUsers.checkUserEmail(manageUsers.existUser(emailChange), emailChange);

    }

    @Test
    public void updatePasswordTest() {
        String passwordChanged = "qwerty2";
        manageUsers.updatePasswordUser(manageUsers.existUser(emailChange), passwordChanged);
        manageUsers.checkUserPassword(manageUsers.existUser(emailChange), passwordChanged);

    }

    @Test
    public void loggerEnableTest() {
        boolean loggedTest1 = true;
        manageUsers.updateActiveUser(manageUsers.existUser(emailChange), loggedTest1);
        manageUsers.checkLogedUser(manageUsers.existUser(emailChange));
    }

    @Test
    public void loggerDisableTest() {
        boolean loggedTest2 = false;
        manageUsers.updateActiveUser(manageUsers.existUser(emailChange), loggedTest2);
        manageUsers.checkLogedUser(manageUsers.existUser(emailChange));
    }

    @Test
    public void deleteTest() {
        manageUsers.deleteUser(manageUsers.existUser(emailChange));
        Assertions.assertEquals(-1, manageUsers.existUser(emailChange));
    }


}
