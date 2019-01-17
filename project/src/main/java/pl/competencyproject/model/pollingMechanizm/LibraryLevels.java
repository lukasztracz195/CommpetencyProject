package pl.competencyproject.model.pollingMechanizm;

import pl.competencyproject.model.dao.classes.ManageLevels;
import pl.competencyproject.model.entities.Level;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryLevels {
    public static LibraryLevels instance;
    private Map<String, Integer> mapLevels;
    private ManageLevels ML;

    private LibraryLevels(boolean test) {
        if (!test) {
            ML = new ManageLevels(TypeOfUsedDatabase.OnlineOrginalDatabase);
        } else {
            ML = new ManageLevels(TypeOfUsedDatabase.OfflineTestDataBase);
        }
        List<Level> listEntitlesLevels = ML.getAllLevels();
        mapLevels = new HashMap<>();
        for (int i = 0; i < listEntitlesLevels.size(); i++) {
            Level tmpEntitle = listEntitlesLevels.get(i);
            mapLevels.put(tmpEntitle.getNameLevel(), tmpEntitle.getIdLevel());
        }
    }

    public static LibraryLevels getInstance(boolean test) {
        if (instance == null) {
            synchronized (LibraryLevels.class) {
                if (instance == null) {
                    instance = new LibraryLevels(test);
                }
            }
        }
        return instance;
    }

    public Integer getExistLevelId(String nameLevel) {
        Integer result = mapLevels.get(nameLevel);
        if (result == null) {
            return -1;
        }
        return result;
    }

    public void update(Integer IdLevel, String nameLevel)
    {
        mapLevels.put(nameLevel, IdLevel);
    }

    public void update(Level levelEntitle)
    {
        mapLevels.put(levelEntitle.getNameLevel(), levelEntitle.getIdLevel());
    }

    public Integer getNumberOfLevels()
    {
        return mapLevels.size();
    }
}
