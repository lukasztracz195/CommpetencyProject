package pl.competencyproject.model.DAO;

import java.util.Random;

public class SessionLogon {

    public static int IdLoggedUser = -1;
    public static boolean correctPassword = false;
    public static boolean logged = false;
    public static int genereatedCode;

    public static void logIn(String email, String password) {
        IdLoggedUser = ManageUsers.existUser(email);
        if (IdLoggedUser >= 0) {
            if (ManageUsers.checkUserPassword(IdLoggedUser, password)) {
                correctPassword = true;
                if (!ManageUsers.checkLogedUser(IdLoggedUser)) {
                    ManageUsers.updateActiveUser(IdLoggedUser, true);
                    logged = true;
                } else logged = false;
            } else correctPassword = false;
        }
    }

    public static void logOut() {
        if (IdLoggedUser > 0) {
            ManageUsers.updateActiveUser(IdLoggedUser, false);
            logged = false;
        }
    }

    public static void sign(String email, String password) {
        IdLoggedUser = ManageUsers.addUser(email, password);
        if(IdLoggedUser != -1)  logIn(email, password);

    }

    public static int generateCode(){
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        genereatedCode = code;
        return code;
    }

    public static int generateCode(){
        Random random = new Random();
        int code = random.nextInt(8999) + 1000;
        genereatedCode = code;
        return code;
    }
}
