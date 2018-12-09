package pl.competencyproject.model.dao.classes;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import pl.competencyproject.model.connection.SessionFactoryConfig;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

public abstract class GeneralManager {

    private static GeneralManager instance;
    protected static SessionFactory sessionFactory;
    protected static Session session;

    protected GeneralManager(TypeOfUsedDatabase type) {
        sessionFactory = SessionFactoryConfig.getSessionFactory(type);
        session = sessionFactory.openSession();
    }

    public void closeSession() {
        if (session.isOpen()) session.close();

    }

}
