package pl.competencyproject.model.dao;

import pl.competencyproject.model.dao.classes.ManageUsers;
import pl.competencyproject.model.Time.GeneralClock;
import pl.competencyproject.model.entities.User;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.Random;

public class SessionLogon {

    public static Integer IdLoggedUser = -1;
    public static boolean correctPassword = false;
    public static boolean logged = false;
    public static Integer genereatedCode;
    public static GeneralClock time;
    private ManageUsers manageUsers = ManageUsers.getInstance(TypeOfUsedDatabase.OnlineOrginalDatabase);
    private static SessionLogon instance;
    public static String email;

    private SessionLogon() {

    }

    public static SessionLogon getInstance() {
        if (instance == null) {
            synchronized (SessionLogon.class) {
                if (instance == null) {
                    instance = new SessionLogon();
                }
            }
        }
        return instance;
    }

    public void logIn(String email, String password) {
        if (manageUsers.existUser(email) != -1) {
            User tmpUser = manageUsers.getUser(email);
            if (tmpUser != null) {
                IdLoggedUser = tmpUser.getIdUser();
                if (manageUsers.encryptSHA1(password).equals(tmpUser.getPassword())) {
                    correctPassword = true;
                    if (!tmpUser.isActive()) {
                        manageUsers.updateActiveUser(IdLoggedUser, true);
                        logged = true;
                    }
                }
            }
            doSomethingbyLoggedUser();
        }
    }

    public void logOut() {
        if (manageUsers.getUser(IdLoggedUser) != null) {
            manageUsers.updateActiveUser(IdLoggedUser, false);
        }
        logged = false;
        correctPassword = false;
        IdLoggedUser = -1;
    }

    public void sign(String email, String password) {
        IdLoggedUser = manageUsers.addUser(email, password);
        if (IdLoggedUser != -1) logIn(email, password);

    }

    public int generateCode() {
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        genereatedCode = code;
        return code;
    }

    public boolean checkCode(String text) {
        int code = Integer.valueOf(text);
        if (code == genereatedCode) {
            return true;
        }
        return false;
    }

    public GeneralClock getClockDate() {
        return time;
    }

    public void resetSession() {
        manageUsers.reset();
    }

    public void closeSession(){
        manageUsers.closeSession();
    }

    public void doSomethingbyLoggedUser() {
        if (SessionLogon.IdLoggedUser != -1) {
        }
    }
}
