package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Family;

public interface ManagingFamily {

    public Integer addFamily(int idLevel, String headFamily);

    public Integer existFamily(int idLevel, String headFamily);

    public Family getFamily(int idFamili);

    public void deleteFamily(Integer idFamily);

}
