package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Dictionary_Words;

import java.util.List;

public interface ManagingDictionaryWords {

    public Integer insertDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);

    //public Integer insertDictionaryWordswithoutLevel(Integer idFamily, Integer idWordENG, Integer idWordPL);

    public List<Dictionary_Words> getDictionaryByLevel(Integer idLevel);

    public List<Dictionary_Words> getDictionaryByFamilie(Integer idFamily);

    public Integer existDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);
}
