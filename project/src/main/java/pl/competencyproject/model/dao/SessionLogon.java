package pl.competencyproject.model.dao;

import pl.competencyproject.model.dao.classes.ManageUsers;
import pl.competencyproject.model.Time.GeneralClock;
import pl.competencyproject.model.entities.User;
import pl.competencyproject.model.enums.TypeDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.DictionaryMap;
import pl.competencyproject.model.mechanicsOfQuestion.Word;
import pl.competencyproject.model.messages.Email;

import java.util.*;

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

    public boolean checkCode(String text) {
        int code = Integer.valueOf(text);
        if (code == genereatedCode) {
            return true;
        }
        return false;
    }

    public int generateCode() {
       Random random = new Random(System.currentTimeMillis());
       int code = random.nextInt(8999) + 1000;
       genereatedCode = code;
        return code;
    }

    public boolean checkCodeForEmailUpdate(String oldEmail,String newEmail) {
        int code1 = Integer.valueOf(oldEmail);
        int code2 = Integer.valueOf(newEmail);
        String concat = Integer.toString(code1) + Integer.toString(code2);
        int code = Integer.parseInt(concat);
        int code3 = Integer.valueOf(Email.oldCode);
        int code4 = Integer.valueOf(Email.newCode);
        String concat1 = Integer.toString(code3) + Integer.toString(code4);
        genereatedCode = Integer.parseInt(concat1);
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

    public void closeSession() {
        manageUsers.closeSession();
    }

    public void doSomethingbyLoggedUser() {
        if (SessionLogon.IdLoggedUser != -1) {
            /*
            DictionaryMap map = DictionaryMap.getInstance();
            map.loadDictionary(1, TypeDictionaryDownloaded.DictionaryOfWords, TypeOfDictionaryLanguage.PLtoENG, TypeOfUsedDatabase.OnlineOrginalDatabase);
            System.out.println("Keys");
            Map<Integer, Word> keys = map.getKeysAllMap();
            for (Map.Entry<Integer, Word> entry : keys.entrySet()) {
                Integer key = entry.getKey();
                Word value = entry.getValue();
                System.out.println(key.toString() + ": " + value.toString());
            }
            System.out.println("Values");
            Map<String, List<String>> values = map.getDictionary();
            for (Map.Entry<String, List<String>> entry : values.entrySet()) {
                String key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.println(key.toString() + ": " + value.toString());
            }
            System.out.println("Dictionary");
            Map<Word, List<String>> dictionary = map.getRandTenMap();
            for (Map.Entry<Word, List<String>> entry : dictionary.entrySet()) {
                Word key = entry.getKey();
                List<String> value = entry.getValue();
                System.out.println(key.toString() + ": " + value.toString());
            }
            */
        }

    }
}
