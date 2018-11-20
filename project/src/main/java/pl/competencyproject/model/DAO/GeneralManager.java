package pl.competencyproject.model.DAO;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import pl.competencyproject.model.connection.SessionFactoryConfig;

public abstract class GeneralManager {

    private  static GeneralManager instance;
    protected  static SessionFactory sessionFactory;
    protected static Session session;

    protected GeneralManager(boolean test ){
        if(!test){
        sessionFactory = SessionFactoryConfig.getSessionFactory();}
        else{ sessionFactory = SessionFactoryConfig.getTestSessionFactory(); }
        session = sessionFactory.openSession();
    }
/*
    public void  trancateTable(String table){
        Transaction tx = null;
            if (!session.isOpen()) {
                session = sessionFactory.openSession();
            }
        try {
            tx = session.beginTransaction();
            StringBuilder sb = new StringBuilder();
            sb.append("TRUNCATE TABLE "+table+";");
            System.out.println("USUWAM");
            NativeQuery query = session.createSQLQuery(sb.toString());
            query.executeUpdate();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
*/
    protected void closeSession(){
        if(session.isOpen()) session.close();
    }

}
