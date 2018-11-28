package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Dictionary_Words;

import java.util.List;

public interface ManagingDictionaryWords {

    public Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);

    public Integer insertDictionaryWordswithoutLevel(int idFamilie, int idWordENG, int idWordPL);

    public List<Dictionary_Words> getDictionaryByLevel(int idLevel);

    public List<Dictionary_Words> getDictionaryByFamilie(int idFamilie);

    public Integer existDictionaryWords(Integer idLevel, Integer idFamilie, Integer idWordENG, Integer idWordPL);

    public Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);
}
