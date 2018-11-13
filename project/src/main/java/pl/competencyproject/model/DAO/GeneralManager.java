package pl.competencyproject.model.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.competencyproject.model.connection.SessionFactoryConfig;

public abstract class GeneralManager {

    protected  static ManageUsers instance;
    protected  static org.hibernate.SessionFactory SessionFactory;
    protected Session session;

    protected GeneralManager(){
        SessionFactory = SessionFactoryConfig.getSessionFactory();
        session = SessionFactory.openSession();
    }

    protected void closeSession(){
        if(session.isOpen()) session.close();
    }

}
