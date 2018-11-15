package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.entities.User;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.entities.Word_PL;

import java.util.List;

public class ManageWordsPL extends GeneralManager{

    private static ManageWordsPL instance;

    private ManageWordsPL() {
        super();
    }

    public static ManageWordsPL getInstance() {
        if (instance == null) {
            synchronized (ManageWordsPL.class) {
                if (instance == null) {
                    instance = new ManageWordsPL();
                }
            }
        }
        return instance;
    }

    public int addWordPL(String strPL) {
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

    public int existWordPL(String strPL) {
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
