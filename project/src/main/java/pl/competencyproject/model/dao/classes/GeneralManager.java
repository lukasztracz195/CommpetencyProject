package pl.competencyproject.model.dao.classes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public abstract class GeneralManager {

    protected  SessionFactory sessionFactory;
    protected  Session session;
    protected TypeOfUsedDatabase type;

    protected GeneralManager(TypeOfUsedDatabase type) {
        this.type = type;
        sessionFactory = SessionFactoryConfig.getSessionFactory(type);
        session = sessionFactory.openSession();
    }

    protected void reset(){
        if(sessionFactory.isClosed()){
            sessionFactory = SessionFactoryConfig.getSessionFactory(type);
        }else{
            if(!session.isOpen()){
                session = sessionFactory.openSession();
            }
        }
    }
    protected void closeSession(){
        SessionFactoryConfig.close();
    }

}
