package pl.competencyproject.model.DW.interfaces;

import pl.competencyproject.model.DW.DictionaryMap;
import pl.competencyproject.model.DW.Word;
import pl.competencyproject.model.enums.TypeDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import java.math.BigInteger;
import java.util.List;
import java.util.SortedMap;

public interface IDictionaryMap {

    public DictionaryMap getInstance();

    public Integer getSizeOfFullMap();

    public void loadDictionary(Integer IdDictionary, TypeDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, boolean test);

    public SortedMap<Word, List<String>> getRandTenMap();

    public Integer getNumberMaxOfSessions();

    public Integer calculateTheNumberOfCombinations();

    public Integer findUniqueID();

    public void lightReset();

    public void hardReset();

    /*

    Ok instrukcja jak to testować
    Najpier ttworzysz plik CSV w resources taki dość długi
    następnie dodajesz go do Bazy Danych
    na daną nazwę poziomu
    potem pobierasz id tego rekordu
    Następnie tworzysz obiekt DictionaryMap przez getInstance()
    Następnie używamy metody initDictiionary()
    Po czym tworzą się struktury danych do odpytywania
    należy sprawdzić czy tyle ile miałeś rekordów w csv ( każde eng przpisane do inNEGO PL sie zgadza z tą w pamięci
    itd.
     */

}
