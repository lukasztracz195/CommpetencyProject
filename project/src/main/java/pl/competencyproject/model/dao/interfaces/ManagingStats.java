package pl.competencyproject.model.dao.interfaces;

import org.hibernate.HibernateException;
import pl.competencyproject.model.entities.Stat;

import java.util.List;

public interface ManagingStats {

    public  Integer addStat(int IdLevel, double valueProgress);

    public Stat getStat(int IdStat);

    public void updateStat(int IdStat, double valueProgress);

    public void deleteStat(int IdStat);

    public Integer existStat(int idStat) throws HibernateException;

    public List getAllStatsOnLevel(int idLevel);

    public List getAllStatsForUser();

    public List GetAllStatsOnLevelForUser(int idLevel);
}
