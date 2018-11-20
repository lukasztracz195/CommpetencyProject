package pl.competencyproject.model.connection;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SessionFactoryConfig {

    private static Map<String, SessionFactory> sessionFactories = new HashMap<>();

    private SessionFactoryConfig() {

    }

    public synchronized static SessionFactory getSessionFactory() {
        String configFileName = "hibernate.cfg.xml";
        SessionFactory session = sessionFactories.get(configFileName);

        if (session == null || session.isClosed()) {
            session = configure(configFileName);
            sessionFactories.put(configFileName, session);
        }

        return session;
    }

    public synchronized static SessionFactory getTestSessionFactory() {
        String testConfigFileName = "hiberante_test.cfg.xml";
        SessionFactory session = sessionFactories.get(testConfigFileName);

        if (session == null || session.isClosed()) {
            session = configure(testConfigFileName);
            sessionFactories.put(testConfigFileName, session);
        }

        return session;
    }

    private static SessionFactory configure(String configFileName) {

        return new Configuration().configure(configFileName).buildSessionFactory();
    }

}