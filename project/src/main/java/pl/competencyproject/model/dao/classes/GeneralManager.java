package pl.competencyproject.model.dao.classes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public abstract class GeneralManager {

    private static GeneralManager instance;
    protected static SessionFactory sessionFactory;
    protected static Session session;
    private TypeOfUsedDatabase type;

    protected GeneralManager(TypeOfUsedDatabase type) {
        this.type = type;
        sessionFactory = SessionFactoryConfig.getSessionFactory(type);
        session = sessionFactory.openSession();
    }

    protected void reset() {
        if (session.isOpen()) SessionFactoryConfig.close();
        sessionFactory = SessionFactoryConfig.getSessionFactory(type);
        session = sessionFactory.openSession();
    }

    protected void closeSession(){
        SessionFactoryConfig.close();
    }

}
