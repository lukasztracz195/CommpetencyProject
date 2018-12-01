package pl.competencyproject.model.DAO.interfaces;

import org.hibernate.HibernateException;
import pl.competencyproject.model.entities.Stat;

public interface ManagingStats {

    public  Integer addStat(int IdLevel, double valueProgress);

    public Stat getStat(int IdStat);

    public void updateStat(int IdStat, double valueProgress);

    public void deleteStat(int IdStat);

    public Integer existStat(int idStat) throws HibernateException;


}
