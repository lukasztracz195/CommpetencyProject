package pl.competencyproject.model.DW.interfaces;

import pl.competencyproject.model.entities.Level;

public interface ILibraryLevels
{
    public Integer getExistLevelId(String nameLevel);

    public void update(Integer IdLevel, String nameLevel);

    public void update(Level levelEntitle);

    public Integer getNumberOfLevels();
}
