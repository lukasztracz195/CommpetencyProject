package pl.competencyproject.model.mechanicsOfQuestion;

import lombok.Getter;
import lombok.Setter;
import pl.competencyproject.model.dao.classes.*;
import pl.competencyproject.model.entities.*;
import pl.competencyproject.model.enums.TypeOfDictionaryDownloaded;
import pl.competencyproject.model.enums.TypeOfUsedDatabase;
import pl.competencyproject.model.mechanicsOfQuestion.interfaces.IDictionaryMap;
import pl.competencyproject.model.enums.TypeOfDictionaryLanguage;

import java.util.*;

@Getter
@Setter
public class DictionaryMap implements IDictionaryMap {

    private static DictionaryMap instance;

    private List<Dictionary_Word> dictionaryWordsFromBase;
    private List<Dictionary_Sentence> dictionarySentencysFromBase;

    private Map<String, List<String>> dictionary;
    private Map<Integer, Word> keysAllMap;
    private Set<Integer> collectionOfuniqueness;

    private String nameOfLevel;
    private String nameOfCategory;
    private String headOfFamily;

    private TypeOfDictionaryDownloaded typeDictionary;
    private TypeOfDictionaryLanguage typeLangToLang;
    private TypeOfUsedDatabase typeDB;

    private int numberMaxOfSessions;
    private int sizeOfFullMap;
    private int numberOfRecordsToDownload;
    private int currentSession;

    private Integer idLevel = -1;
    private Integer idFamily = -1;


    private DictionaryMap() {
        collectionOfuniqueness = new HashSet<>();
        dictionary = new HashMap<>();
        keysAllMap = new HashMap<>();
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
    public SortedMap<Word, List<String>> getRandTenMap() {

        SortedMap<Word, List<String>> partMap = new TreeMap<>();
        if (collectionOfuniqueness.size() < sizeOfFullMap) {

            Integer id;
            int size = 10;
            int difference = sizeOfFullMap - collectionOfuniqueness.size();
            if (difference < 10) {
                size = difference;
            }
            for (int i = 0; i < size; i++) {

                id = findUniqueID();
                Word insertedKey = keysAllMap.get(id);
                List<String> value = dictionary.get(insertedKey.getWord());
                partMap.put(insertedKey, value);
            }
        }
        currentSession++;
        return partMap;
    }

    @Override
    public Integer calculateTheNumberOfCombinations() {
        if (sizeOfFullMap > 10) {
            int result = ((sizeOfFullMap / 10));
            if (sizeOfFullMap % 10 != 0) {
                result++;
            }
            return result;
        } else return 1;
    }

    @Override
    public void setDictionaryOfSentences(String nameOfLevel, String nameOfCategory) {
        resetCordsToDB();
        this.nameOfLevel = nameOfLevel;
        this.nameOfCategory = nameOfCategory;
        this.typeDictionary = TypeOfDictionaryDownloaded.DictionaryOfSentences;
        prepareIDs();
    }

    @Override
    public void setDictionaryOfWords(String nameOfLevel, String nameOfCategory) {
        resetCordsToDB();
        this.nameOfLevel = nameOfLevel;
        this.nameOfCategory = nameOfCategory;
        this.typeDictionary = TypeOfDictionaryDownloaded.DictionaryOfWords;
        prepareIDs();
    }

    @Override
    public void setDictionaryOfFamily(String headOfFamily) {
        resetCordsToDB();
        this.headOfFamily = headOfFamily;
        this.typeDictionary = TypeOfDictionaryDownloaded.DictionaryOfFamilys;
        prepareIDs();
    }


    public void initDownloadDate() {
        if (typeLangToLang != null) {
            if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfWords)) {
                initDictionaryofWordsOrDictionarysFamilys(idLevel, typeDictionary, typeLangToLang, typeDB);
            }
            if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfFamilys)) {
                initDictionaryofWordsOrDictionarysFamilys(idFamily, typeDictionary, typeLangToLang, typeDB);
            }
            if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfSentences)) {
                initDictionaryOfSentencys(idLevel, typeLangToLang, typeDB);
            }
        }
    }

    //dasz to ogarnąć do jutra ?nw bo troche glowa mnie pobolewa i juz tak ze 7 godzin siedze, znaczy konkretnie coprawic

    public void lightReset() {
        this.currentSession = 0;
        this.collectionOfuniqueness.clear();
    }

    public void hardReset() {
        lightReset();
        this.dictionary.clear();
        this.keysAllMap.clear();
        if (this.dictionarySentencysFromBase != null) {
            this.dictionarySentencysFromBase.clear();
        }
        if (this.dictionaryWordsFromBase != null) {
            this.dictionaryWordsFromBase.clear();
        }
        this.sizeOfFullMap = 0;
    }

    private void setNumberOfRecordsToDownload() {
        if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfWords)) {
            ManageDictionaryWords MDW = new ManageDictionaryWords(typeDB);
            numberOfRecordsToDownload = MDW.countDictionaryMap(idLevel);
        } else if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfSentences)) {
            ManageDictionarySentences MDS = new ManageDictionarySentences(typeDB);
            numberOfRecordsToDownload = MDS.countSentencys(idLevel);
        }
    }

    private void resetCordsToDB() {
        nameOfLevel = null;
        nameOfCategory = null;
        headOfFamily = null;
        typeLangToLang = null;
    }

    private void prepareIDs() {
        ManageLevels ML = new ManageLevels(typeDB);
        idLevel = ML.existLevel(nameOfLevel, nameOfCategory);
        setNumberOfRecordsToDownload();
        if (typeDictionary.equals(TypeOfDictionaryDownloaded.DictionaryOfFamilys)) {
            ManageFamily MF = new ManageFamily(typeDB);
            idFamily = MF.existFamily(headOfFamily);
            Family F = MF.getFamily(idFamily);
            idLevel = F.getIdLevel();
            numberOfRecordsToDownload = MF.countFamilys(idFamily);
        }

    }

    private Integer findUniqueID() {
        Random random = new Random();
        Integer selected = -1;
        boolean existInSet = false;
        do {
            selected = random.nextInt(dictionary.size());
            existInSet = collectionOfuniqueness.contains(selected);
        } while (existInSet);
        collectionOfuniqueness.add(selected);
        return selected;
    }

    private void initDictionaryofWordsOrDictionarysFamilys(Integer idDictionary, TypeOfDictionaryDownloaded type, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionaryWords MDW = new ManageDictionaryWords(typeDB);
        ManageWordsENG MWE = new ManageWordsENG(typeDB);
        ManageWordsPL MWP = new ManageWordsPL(typeDB);

        if (type == TypeOfDictionaryDownloaded.DictionaryOfWords) {
            dictionaryWordsFromBase = MDW.getDictionaryByLevel(idDictionary);
        } else if (type == TypeOfDictionaryDownloaded.DictionaryOfFamilys) {
            dictionaryWordsFromBase = MDW.getDictionaryByFamilie(idDictionary);
        }

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
        numberMaxOfSessions = 3 * calculateTheNumberOfCombinations();
    }


    private void initDictionaryOfSentencys(Integer idDictionary, TypeOfDictionaryLanguage typeLanguage, TypeOfUsedDatabase typeDB) {
        ManageDictionarySentences MDS;
        MDS = new ManageDictionarySentences(typeDB);
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
        numberMaxOfSessions = 3 * calculateTheNumberOfCombinations();
    }
}

