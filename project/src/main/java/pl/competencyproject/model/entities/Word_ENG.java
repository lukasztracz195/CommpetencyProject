package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_ENG")
public class Word_ENG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idWordENG;

    private String wordENG;

    public Word_ENG(){}

    public Word_ENG(String wordENG) {
        this.wordENG= wordENG;
    }

    public int getIdWordENG() {
        return idWordENG;
    }

    public String getWordENG() {
        return wordENG;
    }
}
