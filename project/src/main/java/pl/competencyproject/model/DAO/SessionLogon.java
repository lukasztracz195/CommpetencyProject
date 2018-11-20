package pl.competencyproject.model.DAO;

import pl.competencyproject.model.CSV.CSVReader;
import pl.competencyproject.model.Time.GeneralClock;
import pl.competencyproject.model.entities.User;

import java.util.Random;

public class SessionLogon {

    public static int IdLoggedUser = -1;
    public static boolean correctPassword = false;
    public static boolean logged = false;
    public static int genereatedCode;
    public static GeneralClock time;
    private ManageUsers manageUsers = ManageUsers.getInstance();
    private static SessionLogon instance;

    private SessionLogon() {

    }

    public static SessionLogon getInstance() {
        if (instance == null) {
            synchronized (GeneralClock.class) {
                if (instance == null) {
                    instance = new SessionLogon();
                }
            }
        }
        return instance;
    }

    public void logIn(String email, String password) {
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

    public void logOut() {
        if (IdLoggedUser > 0) {
            manageUsers.updateActiveUser(IdLoggedUser, false);
            logged = false;
            correctPassword = false;
            IdLoggedUser = -1;
        }
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

    public void closeSession() {
        manageUsers.closeSession();
    }

    public void doSomethingbyLoggedUser(){
        if(SessionLogon.IdLoggedUser != -1){
        }
    }
}
