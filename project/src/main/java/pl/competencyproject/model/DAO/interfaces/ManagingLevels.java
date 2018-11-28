package pl.competencyproject.model.DAO.interfaces;

import org.hibernate.HibernateException;
import pl.competencyproject.model.entities.Level;

import java.util.List;

public interface ManagingLevels {

    public Integer addLevel(String nameLevel, String nameOfCategory);

    public void deleteLevel(int idLevel);

    public Integer existLevel(String nameLevel, String nameOfCategory) throws HibernateException;

    public Level getLevel(int idLevel);

    public List<Level> getAllLevels();

    public List<String> getCategories(String nameLevel);

    public List<String> getNamesLevels();
}
