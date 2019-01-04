package pl.competencyproject.model.dao.classes;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.dao.SessionLogon;
import pl.competencyproject.model.dao.interfaces.ManagingFamily;
import pl.competencyproject.model.entities.Family;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.List;

public class ManageFamily extends GeneralManager implements ManagingFamily {


    public static final String TABLE = "FAMILIES";

    public  ManageFamily(TypeOfUsedDatabase type) {
        super(type);
    }

    public synchronized Integer addFamily(int idLevel, String headFamily) {
        Transaction tx = null;
        int idDictionary = -1;
        if (SessionLogon.IdLoggedUser > 0 && existFamily(headFamily) == -1) {
            reset();
            try {
                tx = session.beginTransaction();
                Family dictionary = new Family(idLevel, headFamily);
                idDictionary = (Integer) session.save(dictionary);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return idDictionary;
    }

    public synchronized Integer existFamily( String headFamily) {
        reset();
        Family family = null;
        NativeQuery query = session.createSQLQuery("SELECT * FROM FAMILIES WHERE headFamily = :headFamily");
        query.addEntity(Family.class);
        query.setParameter("headFamily", headFamily);
        List result = query.list();
        if (result.size() != 0) {
            family = (Family) result.get(0);
            return family.getIdFamily();
        }
        return -1;
    }

    public synchronized Family getFamily(int idFamily) {
        Transaction tx = null;
        Family family = null;
        try {
            reset();
            tx = session.beginTransaction();
            family = (Family) session.get(Family.class, idFamily);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return family;
    }

    public synchronized void deleteFamily(Integer idFamily) {
        if (getFamily(idFamily) != null) {
            Transaction tx = null;
            reset();
            try {
                tx = session.beginTransaction();
                Family family = session.get(Family.class, idFamily);
                session.delete(family);
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
    }

    public synchronized Integer countFamilys(int idFamily){
        reset();
        NativeQuery query = session.createSQLQuery("SELECT idFamilie FROM FAMILIES WHERE idFamily = :idFamily");
        query.setParameter("idFamily", idFamily);
        return query.getMaxResults();
    }
}
