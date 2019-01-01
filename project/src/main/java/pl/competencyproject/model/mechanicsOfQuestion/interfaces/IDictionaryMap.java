package pl.competencyproject.model.mechanicsOfQuestion.interfaces;

import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.Word;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import java.util.List;
import java.util.SortedMap;

public interface IDictionaryMap {

    public int getSizeOfFullMap();

    public void loadDictionary(Integer IdDictionary, TypeOfDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB);

    public SortedMap<Word, List<String>> getRandTenMap();

    public Integer calculateTheNumberOfCombinations();

    public Integer findUniqueID();

}
