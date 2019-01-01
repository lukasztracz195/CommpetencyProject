package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.interfaces.ManagingLevels;
import pl.competencyproject.model.entities.Level;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.LinkedList;
import java.util.List;

public class ManageLevels extends GeneralManager implements ManagingLevels {

    public static final String TABLE = "LEVELS";

    public ManageLevels(TypeOfUsedDatabase type) {
        super(type);
    }



    public synchronized Integer addLevel(String nameLevel, String nameCategorie) {
        Transaction tx = null;
        int idLevel = -1;
        if (this.existLevel(nameLevel, nameCategorie) == -1) {
            try {
                reset();
                tx = session.beginTransaction();
                Level level = new Level(nameLevel, nameCategorie);
                idLevel = (Integer) session.save(level);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idLevel;
    }

    /* Method to DELETE a level from the records */
    public  void deleteLevel(int idLevel) {
        Transaction tx = null;
        try {
            reset();
            tx = session.beginTransaction();
            Level level = (Level) session.get(Level.class, idLevel);
            session.delete(level);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to check if level does EXIST*/
    public synchronized Integer existLevel(String nameLevel, String nameCategorie) throws HibernateException {

        Level level = null;
        Level level2 = null;
        int id = -1;
        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM LEVELS WHERE nameLevel = :nameLevel AND nameCategorie = :nameCategorie");
        query.addEntity(Level.class);
        query.setParameter("nameLevel", nameLevel);
        query.setParameter("nameCategorie", nameCategorie);
        List result = query.list();
        if (result.size() != 0) {
            level = (Level) result.get(0);
            id = level.getIdLevel();
        }
        return id;
    }

    public synchronized Level getLevel(int idLevel) {
        Transaction tx = null;
        Level level = null;
        try {
            reset();
            tx = session.beginTransaction();
            level = (Level) session.get(Level.class, idLevel);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return level;
    }


    public synchronized List<Level> getAllLevels() {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT * FROM LEVELS");
        query.addEntity(Level.class);
        List list = query.list();
        if (list == null) {
            list = new LinkedList();
        }
        return list;
    }

    public synchronized  List<String> getCategories(String nameLevel) {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT nameCategorie FROM LEVELS");
        List result = query.list();
        if (result == null) {
            result = new LinkedList<>();
        }
        return result;
    }

    public synchronized List<String> getNamesLevels() {
        reset();
        NativeQuery query = session.createSQLQuery("SELECT DISTINCT nameLevel FROM LEVELS");
        List result = query.list();
        if (result == null) {
            result = new LinkedList<>();
        }
        return result;
    }

}
