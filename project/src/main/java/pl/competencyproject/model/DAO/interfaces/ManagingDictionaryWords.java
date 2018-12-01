package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Dictionary_Words;

import java.util.List;

public interface ManagingDictionaryWords {

    public Integer insertDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);

    public Integer insertDictionaryWordswithoutLevel(int idFamily, int idWordENG, int idWordPL);

    public List<Dictionary_Words> getDictionaryByLevel(int idLevel);

    public List<Dictionary_Words> getDictionaryByFamilie(int idFamily);

    public Integer existDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);
}
