package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_PL")
public class Word_PL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWordPL;

    private String wordPL;

    public Word_PL(){}

    public Word_PL(String wordPL) {
        this.wordPL = wordPL;
    }

    public int getIdWordPL() {
        return idWordPL;
    }

    public String getWordPL() {
        return wordPL;
    }
}
