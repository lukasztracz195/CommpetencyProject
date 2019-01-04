package pl.competencyproject.model.dao.interfaces;

import pl.competencyproject.model.entities.Family;

public interface ManagingFamily {

    public Integer addFamily(int idLevel, String headFamily);

    public Integer existFamily( String headFamily);

    public Family getFamily(int idFamily);

    public void deleteFamily(Integer idFamily);

}
