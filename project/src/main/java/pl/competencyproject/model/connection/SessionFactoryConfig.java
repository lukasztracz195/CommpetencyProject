package pl.competencyproject.model.connection;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SessionFactoryConfig {

    private static Map<String, SessionFactory> sessionFactories = new HashMap<>();
    private static String configFileName = "hibernate.cfg.xml";
    private static SessionFactory session;

    private SessionFactoryConfig() {

    }

    public synchronized static SessionFactory getSessionFactory() {
        session = sessionFactories.get(getName());

        if (session == null || session.isClosed()) {

            session = configure(getName());
            sessionFactories.put(getName(), session);
        }

        return session;
    }

    private static SessionFactory configure(String configFileName) {
        return new Configuration().configure(configFileName).buildSessionFactory();
    }

    private static String getName(){
        return configFileName;
    }
}