package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.interfaces.ManagingWordsPL;
import pl.competencyproject.model.entities.Word_PL;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageWordsPL extends GeneralManager implements ManagingWordsPL {

    private static ManageWordsPL instance;
    public static final String TABLE = "WORDS_PL";

    private ManageWordsPL(TypeOfUsedDatabase type) {
        super(type);
    }

    public static ManageWordsPL getInstance(TypeOfUsedDatabase type) {
        if (instance == null) {
            synchronized (ManageWordsPL.class) {
                if (instance == null) {
                    instance = new ManageWordsPL(type);
                }
            }
        }
        return instance;
    }

    public static void delete() {
        instance = null;
    }

    public Integer addWordPL(String strPL) {
        Transaction tx = null;
        int idWordPL = -1;

        if (this.existWordPL(strPL) == -1) {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            try {
                tx = session.beginTransaction();
                Word_PL wordPL = new Word_PL(strPL);
                idWordPL = (Integer) session.save(wordPL);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idWordPL;
    }

    public Integer existWordPL(String strPL) {
        if (!session.isOpen()) {
            session = sessionFactory.openSession();
        }
        NativeQuery query = session.createSQLQuery("SELECT * FROM WORDS_PL WHERE wordPL =  :wordPL");
        query.addEntity(Word_PL.class);
        query.setParameter("wordPL", strPL);
        List result = query.list();
        if (result.size() != 0) {
            Word_PL word = (Word_PL) result.get(0);
            return word.getIdWordPL();
        }
        return -1;
    }

    public void deleteWordPL(Integer idWordPL) {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Word_PL user = (Word_PL) session.get(Word_PL.class, idWordPL);
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public Word_PL getWordPL(int idWordPL) {
        Transaction tx = null;
        Word_PL word = null;
        try {
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
            tx = session.beginTransaction();
            word = (Word_PL) session.get(Word_PL.class, idWordPL);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return word;
    }
}
