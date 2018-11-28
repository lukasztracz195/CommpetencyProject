package pl.competencyproject.model.DAO;

import org.junit.jupiter.api.Test;
import pl.competencyproject.model.DAO.classes.ManageDictionarySentences;
import pl.competencyproject.model.DAO.classes.ManageLevels;
import pl.competencyproject.model.entities.Dictionary_Sentences;


public class ManageDictionarySentencesTest {
    private ManageDictionarySentences manageDictionarySentences = ManageDictionarySentences.getTestInstance();

    private int IdLevel = 0;
    private String sentenceENG = "I am a human";
    private String sentencePL = "Jestem czlowiekiem";

    int idDictionary = 0;

    @Test
    void insertDictionarySenteceTest(){
        idDictionary = manageDictionarySentences.insertDictionarySentece(IdLevel,sentenceENG,sentencePL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    void existDictionarySentencestTest(){
        idDictionary = manageDictionarySentences.existDictionarySentences(IdLevel,sentenceENG,sentencePL);
        if(idDictionary == -1) throw new RuntimeException("Error");
    }

    @Test
    void getDictionary_SentencesTest(){
        idDictionary = manageDictionarySentences.existDictionarySentences(IdLevel,sentenceENG,sentencePL);
        Dictionary_Sentences dicSentency = manageDictionarySentences.getDictionary_Sentences(idDictionary);
        dicSentency.equals(null);
    }
}
