package pl.competencyproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_WORDS")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary_Words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionaryWords;

    private Integer idLevel;

    private Integer idFamilie;

    private Integer idWordENG;

    private Integer idWordPL;


    public Dictionary_Words(Integer idLevel, Integer idFamilie, Integer idWordENG, Integer idWordPL) {
        this.idLevel = idLevel;
        this.idFamilie = idFamilie;
        this.idWordENG = idWordENG;
        this.idWordPL = idWordPL;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id:{ " + idDictionaryWords + "} idLevel:{ " + idLevel + " } idFamilie:{ " + idFamilie + " } idWordENG:{ " + idWordENG + " } idWordPL:{ " + idWordPL + " }");
        return sb.toString();
    }
}


