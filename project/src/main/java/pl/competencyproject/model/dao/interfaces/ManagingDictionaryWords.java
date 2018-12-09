package pl.competencyproject.model.dao.interfaces;

import pl.competencyproject.model.entities.Dictionary_Word;

import java.util.List;

public interface ManagingDictionaryWords {

    public Integer insertDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer insertDictionaryWordswithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);

    //public Integer insertDictionaryWordswithoutLevel(Integer idFamily, Integer idWordENG, Integer idWordPL);

    public List<Dictionary_Word> getDictionaryByLevel(Integer idLevel);

    public List<Dictionary_Word> getDictionaryByFamilie(Integer idFamily);

    public Integer existDictionaryWords(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL);

    public Integer existDictionaryWordsWithoutFamilie(Integer idLevel, Integer idWordENG, Integer idWordPL);
}
