package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Dictionary_Sentence;

public interface ManagingDictionarySentences {

    public Integer insertDictionarySentece(int idLevel, String sentencesENG, String sentencesPL);

    public Integer existDictionarySentences(int idLevel, String sentencesENG, String sentencesPL);

    public Dictionary_Sentence getDictionary_Sentences(int idDictionary);

}
