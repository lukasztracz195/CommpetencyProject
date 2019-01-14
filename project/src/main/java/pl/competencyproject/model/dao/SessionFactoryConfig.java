package pl.competencyproject.model.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SessionFactoryConfig {
    private static Map<String, SessionFactory> sessionFactories = new HashMap<>();
    private static final String databaseOriginalOnline = "hibernate_online_original.cfg.xml";
    private static final String databaseTestOnline = "hibernate_online_test.cfg.xml";
    private static final String databaseTestOffline = "hibernate_offline_test.cfg.xml";
    private static String used;

    private SessionFactoryConfig() {
    }

    public synchronized static SessionFactory getSessionFactory(TypeOfUsedDatabase type) {
        String configFileName = chooseConfigFile(type);
        SessionFactory session = sessionFactories.get(configFileName);
        if (session == null || session.isClosed()) {
            session = configure(configFileName);
            sessionFactories.put(configFileName, session);
        }
        return session;
    }

    private static String chooseConfigFile(TypeOfUsedDatabase type) {
        if (type == TypeOfUsedDatabase.OnlineOrginalDatabase) {
            return databaseOriginalOnline;
        } else if (type == TypeOfUsedDatabase.OnlineTestDatabase) {
            return databaseTestOnline;
        } else
            return databaseTestOffline;
    }

    private static SessionFactory configure(String configFileName) {
        used = configFileName;
        return new Configuration().configure(configFileName).buildSessionFactory();
    }

    public static void close() {
        SessionFactory session = sessionFactories.get(used);
        if (session.isOpen()) {
            session.close();
        }
    }
}