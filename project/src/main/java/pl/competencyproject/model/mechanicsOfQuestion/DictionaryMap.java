package pl.competencyproject.model.mechanicsOfQuestion;

import lombok.Getter;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.interfaces.IDictionaryMap;
import pl.competencyproject.model.entities.Dictionary_Sentence;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.entities.Word_PL;
import pl.competencyproject.model.enums.TypeDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import java.util.*;
@Getter
public class DictionaryMap implements IDictionaryMap {

    private static DictionaryMap instance;

    private List<Dictionary_Word> dictionaryWordsFromBase;
    private List<Dictionary_Sentence> dictionarySentencysFromBase;
    private Map<Word, List<String>> dictionary;
    private Map<Integer, Word> keysAllMap;
    private Set<Integer> collectionOfuniqueness;

    private Integer numberMaxOfSessions;

    private Integer sizeOfFullMap = 0;

    private Integer currentSession = 0;

    private DictionaryMap() {
        collectionOfuniqueness = new HashSet<>();

    }

    public static DictionaryMap getInstance() {
        if (instance == null) {
            synchronized (DictionaryMap.class) {
                if (instance == null) {
                    instance = new DictionaryMap();
                }
            }
        }
        return instance;
    }

    @Override
    public Integer getSizeOfFullMap() {
        return sizeOfFullMap;
    }

    @Override
    public void loadDictionary(Integer idDictionary, TypeDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        if (type == TypeDictionaryDownloaded.DictionaryOfWords) {
            initDictionaryofWordsOrDictionarysFamilys(idDictionary, type, typeLanguage, typeDB);
        } else if (type == TypeDictionaryDownloaded.DictionaryOfFamilys) {
            initDictionaryOfSentencys(idDictionary, typeLanguage, typeDB);
        }
            numberMaxOfSessions = calculateTheNumberOfCombinations();

    }

    @Override
    public SortedMap<Word, List<String>> getRandTenMap() {
        System.out.println("getRandTenMap");
        SortedMap<Word, List<String>> partMap = new TreeMap<>();
        if(currentSession <= numberMaxOfSessions) {

            Integer id;
            Integer size = 10;
            if (sizeOfFullMap < 10) {
                size = sizeOfFullMap;
            }
            for (int i = 0; i < size; i++) {
                System.out.println("getRandTenMap1");
                id = findUniqueID();
                System.out.println("getRandTenMap2");
                Word insertedKey = keysAllMap.get(id);
                List<String> value = dictionary.get(insertedKey);
                partMap.put(insertedKey, value);
            }
        }
        return partMap;
    }

    @Override
    public Integer getNumberMaxOfSessions() {
        return numberMaxOfSessions;
    }

    public Integer calculateTheNumberOfCombinations() {
        if(sizeOfFullMap > 10) {
            return sizeOfFullMap % 10;
        }else return 1;
    }

    public Integer findUniqueID() {
        System.out.println("findUniqueID");
        Random random = new Random();
        Integer selected = -1;
        boolean existInSet = false;
        do {
            selected = random.nextInt(sizeOfFullMap);
            existInSet = collectionOfuniqueness.contains(selected);
        } while (existInSet);
        collectionOfuniqueness.add(selected);
        return selected;
    }


    private void initDictionaryofWordsOrDictionarysFamilys(Integer idDictionary, TypeDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionaryWords MDW;
        ManageWordsENG MWE;
        ManageWordsPL MWP;


            MDW = ManageDictionaryWords.getInstance(typeDB);
            MWE = ManageWordsENG.getInstance(typeDB);
            MWP = ManageWordsPL.getInstance(typeDB);

        lightReset();
        if (type == TypeDictionaryDownloaded.DictionaryOfWords) {
            dictionaryWordsFromBase = MDW.getDictionaryByLevel(idDictionary);
        } else if (type == TypeDictionaryDownloaded.DictionaryOfFamilys) {
            dictionaryWordsFromBase = MDW.getDictionaryByFamilie(idDictionary);
        }
        this.dictionary = new HashMap<>();
        keysAllMap = new HashMap<>();
        System.out.println(dictionaryWordsFromBase.size());
        for (int i = 0; i < dictionaryWordsFromBase.size(); i++) {
            Dictionary_Word tmpEntite = dictionaryWordsFromBase.get(i);
            Word_ENG tmpWordENG = MWE.getWordENG(tmpEntite.getIdWordENG());
            Word_PL tmpWordPL = MWP.getWordPL(tmpEntite.getIdWordPL());
            List<String> listValues;
            Word key = null;
            String value = null;
            if (typeLanguage == TypeOfDictionaryLanguage.ENGtoPL) {
                key = new Word(i, tmpWordENG.getWordENG());
                value = tmpWordPL.getWordPL();
            }else if (typeLanguage == TypeOfDictionaryLanguage.PLtoENG) {
                key = new Word(i, tmpWordPL.getWordPL());
                value = tmpWordENG.getWordENG();
            }
            System.out.println(key);
            System.out.println(dictionary);
            if (!dictionary.containsKey(key)) {
                listValues = new ArrayList<>();
                listValues.add(value);
                keysAllMap.put(i, key);
                dictionary.put(key, listValues);
                sizeOfFullMap++;
            } else {
                listValues = dictionary.get(key);
                listValues.add(value);
                dictionary.replace(key, listValues);
            }
        }
    }


    private void initDictionaryOfSentencys(Integer idDictionary, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionarySentences MDS;
            MDS = ManageDictionarySentences.getInstance(typeDB);
        this.dictionary = new HashMap<>();

        lightReset();
        this.dictionary = new HashMap<>();
        keysAllMap = new HashMap<>();
        dictionarySentencysFromBase = MDS.getListbyLevel(idDictionary);
        for (int i = 0; i < dictionarySentencysFromBase.size(); i++) {
            Dictionary_Sentence tmpEntite = dictionarySentencysFromBase.get(i);
            String wordENG = tmpEntite.getSentencesENG();
            String wordPL = tmpEntite.getSentencesPL();
            String value = "";
            Word word = null;
            if (typeLanguage == TypeOfDictionaryLanguage.PLtoENG) {
                word = new Word(i, wordPL);
                value = wordENG;
            } else if (typeLanguage == TypeOfDictionaryLanguage.ENGtoPL) {
                word = new Word(i, wordENG);
                value = wordPL;
            }
            List<String> listS = new ArrayList<>();
            listS.add(value);
            keysAllMap.put(i, word);
            dictionary.put(word, listS);
            sizeOfFullMap++;
        }
    }

    public void lightReset() {
        sizeOfFullMap = 0;
        numberMaxOfSessions = 0;
        dictionary = null;
        keysAllMap = null;
       // Runtime r = Runtime.getRuntime();
       // r.freeMemory();
    }

    public void hardReset() {
        dictionaryWordsFromBase = null;
        dictionarySentencysFromBase = null;
       // lightReset();
    }
}
