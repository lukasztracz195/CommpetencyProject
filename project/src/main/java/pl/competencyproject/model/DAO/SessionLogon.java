package pl.competencyproject.model.DAO;

public class SessionLogon {

    public static int IdLoggedUser = -1;
    public static boolean correctPassword = false;
    public static boolean logged = false;

    public static void login(String email, String password) {
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
        if (IdLoggedUser >= 0) {
            ManageUsers.updateActiveUser(IdLoggedUser, false);
            logged = false;
        }
    }

    public static void sign(String email, String password) {
        IdLoggedUser = ManageUsers.addUser(email, password);
        login(email, password);
    }
}
