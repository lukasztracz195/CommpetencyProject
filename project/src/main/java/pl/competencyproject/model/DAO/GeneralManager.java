package pl.competencyproject.model.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.competencyproject.model.connection.SessionFactoryConfig;

public abstract class GeneralManager {

    private  static GeneralManager instance;
    protected  static SessionFactory sessionFactory;
    protected static Session session;

    protected GeneralManager(){
        sessionFactory = SessionFactoryConfig.getSessionFactory();
        session = sessionFactory.openSession();
    }

    protected void closeSession(){
        if(session.isOpen()) session.close();
    }

}
