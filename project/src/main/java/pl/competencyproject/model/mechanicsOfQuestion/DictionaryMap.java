package pl.competencyproject.model.mechanicsOfQuestion;

import lombok.Getter;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.interfaces.IDictionaryMap;
import pl.competencyproject.model.entities.Dictionary_Sentence;
import pl.competencyproject.model.entities.Dictionary_Word;
import pl.competencyproject.model.entities.Word_ENG;
import pl.competencyproject.model.entities.Word_PL;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import java.util.*;

@Getter
public class DictionaryMap implements IDictionaryMap {

    private static DictionaryMap instance;

    private List<Dictionary_Word> dictionaryWordsFromBase;
    private List<Dictionary_Sentence> dictionarySentencysFromBase;
    private Map<String, List<String>> dictionary;
    private Map<Integer, Word> keysAllMap;
    private Set<Integer> collectionOfuniqueness;

    private int numberMaxOfSessions;

    private int sizeOfFullMap;

    private int currentSession;

    private DictionaryMap() {
        collectionOfuniqueness = new HashSet<>();
        currentSession = 0;
        sizeOfFullMap = 0;
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
    public void loadDictionary(Integer idDictionary, TypeOfDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        if (type == TypeOfDictionaryDownloaded.DictionaryOfWords) {
            initDictionaryofWordsOrDictionarysFamilys(idDictionary, type, typeLanguage, typeDB);
        } else if (type == TypeOfDictionaryDownloaded.DictionaryOfSentences) {
            initDictionaryOfSentencys(idDictionary, typeLanguage, typeDB);
        }
        numberMaxOfSessions = 3*calculateTheNumberOfCombinations();

    }

    public SortedMap<Word, List<String>> getRandTenMap() {

        SortedMap<Word, List<String>> partMap = new TreeMap<>();
        //if (currentSession < numberMaxOfSessions) {

            Integer id;
            int size = 10;
            if (dictionary.size() < 10) {
                size = dictionary.size();
            }
            for (int i = 0; i < size; i++) {

                id = findUniqueID();
                Word insertedKey = keysAllMap.get(id);
                List<String> value = dictionary.get(insertedKey.getWord());
                partMap.put(insertedKey, value);
            }
        //}
        //currentSession++;
        return partMap;
    }

    @Override
    public Integer getNumberMaxOfSessions() {
        return numberMaxOfSessions;
    }

    public Integer calculateTheNumberOfCombinations() {
        if (sizeOfFullMap > 10) {
            return ((sizeOfFullMap / 10));
        } else return 1;
    }

    public Integer findUniqueID() {
        Random random = new Random();
        Integer selected = -1;
        boolean existInSet = false;
        do {
            selected = random.nextInt(dictionary.size() );
            existInSet = collectionOfuniqueness.contains(selected);
        } while (existInSet);
        collectionOfuniqueness.add(selected);
        return selected;
    }


    private void initDictionaryofWordsOrDictionarysFamilys(Integer idDictionary, TypeOfDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionaryWords MDW;
        ManageWordsENG MWE;
        ManageWordsPL MWP;


        MDW = ManageDictionaryWords.getInstance(typeDB);
        MWE = ManageWordsENG.getInstance(typeDB);
        MWP = ManageWordsPL.getInstance(typeDB);
        if (type == TypeOfDictionaryDownloaded.DictionaryOfWords) {
            dictionaryWordsFromBase = MDW.getDictionaryByLevel(idDictionary);
        } else if (type == TypeOfDictionaryDownloaded.DictionaryOfFamilys) {
            dictionaryWordsFromBase = MDW.getDictionaryByFamilie(idDictionary);
        }
        dictionary = new HashMap<>();
        keysAllMap = new HashMap<>();
        for (int i = 0; i < dictionaryWordsFromBase.size(); i++) {
            Dictionary_Word tmpEntite = dictionaryWordsFromBase.get(i);
            Word_ENG tmpWordENG = MWE.getWordENG(tmpEntite.getIdWordENG());
            Word_PL tmpWordPL = MWP.getWordPL(tmpEntite.getIdWordPL());
            List<String> listValues;
            Word key = null;
            String value = null;
            if (typeLanguage == TypeOfDictionaryLanguage.ENGtoPL) {
                key = new Word(tmpWordENG.getWordENG());
                value = tmpWordPL.getWordPL();
            } else if (typeLanguage == TypeOfDictionaryLanguage.PLtoENG) {
                key = new Word(tmpWordPL.getWordPL());
                value = tmpWordENG.getWordENG();
            }
            if (!dictionary.containsKey(key.getWord())) {
                listValues = new ArrayList<>();
                listValues.add(value);
                keysAllMap.put(keysAllMap.size(), key);
                dictionary.put(key.getWord(), listValues);
                sizeOfFullMap++;
            } else {
                listValues = dictionary.get(key.getWord());
                listValues.add(value);
                dictionary.replace(key.getWord(), listValues);
            }
        }
    }


    private void initDictionaryOfSentencys(Integer idDictionary, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionarySentences MDS;
        MDS = ManageDictionarySentences.getInstance(typeDB);
        dictionary = new HashMap<>();
        keysAllMap = new HashMap<>();
        dictionarySentencysFromBase = MDS.getListbyLevel(idDictionary);
        for (int i = 0; i < dictionarySentencysFromBase.size(); i++) {
            Dictionary_Sentence tmpEntite = dictionarySentencysFromBase.get(i);
            String wordENG = tmpEntite.getSentencesENG();
            String wordPL = tmpEntite.getSentencesPL();
            String value = "";
            Word word = null;
            if (typeLanguage == TypeOfDictionaryLanguage.PLtoENG) {
                word = new Word(wordPL);
                value = wordENG;
            } else if (typeLanguage == TypeOfDictionaryLanguage.ENGtoPL) {
                word = new Word(wordENG);
                value = wordPL;
            }
            List<String> listS = new ArrayList<>();
            listS.add(value);
            keysAllMap.put(keysAllMap.size(), word);
            dictionary.put(word.getWord(), listS);
            sizeOfFullMap++;
        }
    }

    public void lightReset(){
        this.currentSession = 0;
        this.collectionOfuniqueness =  new HashSet<>();
    }

    public void hardReset(){
        lightReset();
        this.dictionary = null;
        this.keysAllMap = null;
        this.dictionarySentencysFromBase = null;
        this.dictionaryWordsFromBase = null;
        this.sizeOfFullMap = 0;
    }
}
