package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_WORDS")
public class Dictionary_Words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionaryWords;

    private Integer idLevel;

    private Integer idFamilie;

    private Integer idWordENG;

    private Integer idWordPL;

    public Dictionary_Words() {
    }

    ;

    public Dictionary_Words(int idLevel, int idFamilie, int idWordENG, int idWordPL) {
        this.idLevel = idLevel;
        this.idFamilie = idFamilie;
        this.idWordENG = idWordENG;
        this.idWordPL = idWordPL;
    }

    public Dictionary_Words(int idLevelFamilie, int idWordENG, int idWordPL, boolean LeveltrueFamiliefalse) {

        this.idWordENG = idWordENG;
        this.idWordPL = idWordPL;
        if (LeveltrueFamiliefalse) {
            this.idLevel = idLevelFamilie;
            this.idFamilie = null;
        } else {
            this.idFamilie = idLevelFamilie;
            this.idLevel = null;
        }
    }


    public int getIdDictionaryWords() {
        return idDictionaryWords;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public int getIdFamilie() {
        return idFamilie;
    }

    public void setIdFamilie(int idFamilie) {
        this.idFamilie = idFamilie;
    }

    public int getIdWordENG() {
        return idWordENG;
    }

    public void setIdWordENG(int idWordENG) {
        this.idWordENG = idWordENG;
    }

    public int getIdWordPL() {
        return idWordPL;
    }

    public void setIdWordPL(int idWordPL) {
        this.idWordPL = idWordPL;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id:{ " + idDictionaryWords + "} idLevel:{ " + idLevel + " } idFamilie:{ " + idFamilie + " } idWordENG:{ " + idWordENG + " } idWordPL:{ " + idWordPL + " }");
        return sb.toString();
    }
}


