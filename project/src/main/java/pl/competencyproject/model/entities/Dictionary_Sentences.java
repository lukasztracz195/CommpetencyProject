package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_SENTENCES")
public class Dictionary_Sentences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionary;

    private Integer idLevel;

    private String sentencesENG;

    private String sentencesPL;

    public Dictionary_Sentences(){};

    public Dictionary_Sentences(int idLevel, String sentencesENG, String sentencesPL) {
        this.idLevel = idLevel;
        this.sentencesENG = sentencesENG;
        this.sentencesPL = sentencesPL;
    }

    public int getIdDictionary() {
        return idDictionary;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getSentencesENG() {
        return sentencesENG;
    }

    public void setSentencesENG(String sentencesENG) {
        this.sentencesENG = sentencesENG;
    }

    public String getSentencesPL() {
        return sentencesPL;
    }

    public void setSentencesPL(String sentencesPL) {
        this.sentencesPL = sentencesPL;
    }
}

