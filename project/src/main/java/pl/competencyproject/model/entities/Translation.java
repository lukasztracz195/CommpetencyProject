package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name ="TRANSLATIONS")
public class Translation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTranslation;

    private int idLevel;

    private String engWord;

    private String plWord;

    public Translation(int idLevel, String engWord, String plWord) {
        this.idLevel = idLevel;
        this.engWord = engWord;
        this.plWord = plWord;
    }

    public Translation() {
    }

    public int getIdTranslation() {
        return idTranslation;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public String getEngWord() {
        return engWord;
    }

    public String getPlWord() {
        return plWord;
    }
}
