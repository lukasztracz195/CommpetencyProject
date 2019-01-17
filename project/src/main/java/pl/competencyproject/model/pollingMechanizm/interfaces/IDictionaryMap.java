package pl.competencyproject.model.pollingMechanizm.interfaces;

import pl.competencyproject.model.pollingMechanizm.Word;

import java.util.List;
import java.util.SortedMap;

public interface IDictionaryMap {

    public int getSizeOfFullMap();

    public SortedMap<Word, List<String>> getRandTenMap();

    public Integer calculateTheNumberOfCombinations();

    public void setDictionaryOfSentences(String nameOfLevel, String nameOfCategory);

    public void setDictionaryOfWords(String nameOfLevel, String nameOfCategory);

    public void setDictionaryOfFamily( String headOfFamily);

    public  int getNumberOfRecordsToDownload();



}
