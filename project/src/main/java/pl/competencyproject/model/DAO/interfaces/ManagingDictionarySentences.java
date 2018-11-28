package pl.competencyproject.model.DAO.interfaces;

import pl.competencyproject.model.entities.Dictionary_Sentences;

public interface ManagingDictionarySentences {

    public Integer insertDictionarySentece(int idLevel, String sentencesENG, String sentencesPL);

    public Integer existDictionarySentences(int idLevel, String sentencesENG, String sentencesPL);

    public Dictionary_Sentences getDictionary_Sentences(int idDictionary);

}
