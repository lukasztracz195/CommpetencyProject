package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.interfaces.ManagingWordsPL;
import pl.competencyproject.model.entities.Word_PL;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageWordsPL extends GeneralManager implements ManagingWordsPL {


    public static final String TABLE = "WORDS_PL";

    public ManageWordsPL(TypeOfUsedDatabase type) {
        super(type);
    }

    public synchronized Integer addWordPL(String strPL) {
        Transaction tx = null;
        int idWordPL = -1;

        if (this.existWordPL(strPL) == -1) {
            reset();
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

    public synchronized Integer existWordPL(String strPL) {
        reset();
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

    public synchronized void deleteWordPL(Integer idWordPL) {
        Transaction tx = null;
        try {
            reset();
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

    public synchronized Word_PL getWordPL(int idWordPL) {
        Transaction tx = null;
        Word_PL word = null;
        try {
            reset();
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
