package pl.project_competencies.connection;

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

    public synchronized static SessionFactory getSessionFactory(String configFileName) {

        SessionFactory result = sessionFactories.get(configFileName);

        if (result == null || result.isClosed()) {

            result = configure(configFileName);
            sessionFactories.put(configFileName, result);
        }

        return result;
    }

    private static SessionFactory configure(String configFileName) {
        return new Configuration().configure(configFileName).buildSessionFactory();
    }
}