package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageLevels;
import pl.competencyproject.model.DAO.classes.ManageStats;
import pl.competencyproject.model.DAO.classes.ManageUsers;
import pl.competencyproject.model.entities.Stat;

import java.util.Date;

import static java.lang.System.exit;
import static java.sql.JDBCType.NULL;


public class ManageStatTest {

    //private  int idLevel = 1;
    private   double valProgress = 75.4;
    private   double valProgress1 = 22.1;
    private ManageStats manageStat = ManageStats.getTestInstance();
    private ManageUsers manageUsers = ManageUsers.getTestInstance();
    private ManageLevels manageLevels = ManageLevels.getTestInstance();

    private String nameLevel = "B2";
    private String nameCategorie = "Working life";
    private String email = "testUser3@gmail.com";
    private String password = "qwerty3";
    private int IdStat = 0;

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
        int idLevel = manageLevels.existLevel(nameLevel,nameCategorie);
        if(idLevel == -1){
            idLevel = manageLevels.addLevel(nameLevel,nameCategorie);
        }
        IdStat = manageStat.addStat(idLevel,valProgress);
        int result = manageStat.existStat(IdStat);
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void getTest(){
        Stat a = manageStat.getStat(IdStat);
        a.equals(null);
    }

//    @Test
//    public void updateTest(){
//        manageStat.updateStat(IdStat,valProgress1);
//        Stat a = manageStat.getStat(IdStat);
//        if(a.getValueProgress() != valProgress1) exit(1);
//    }

    @Test
    public void deleteTest(){
        manageStat.deleteStat(IdStat);
        int result = manageStat.existStat(IdStat );
        if(result == -1) throw new RuntimeException("Error");
    }

    @Test
    public void existTest()
    {
        int result = manageStat.existStat(IdStat );
        if(result == -1) throw new RuntimeException("Error");

    }















}
