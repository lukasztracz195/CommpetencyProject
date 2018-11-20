package pl.competencyproject.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_SENTENCES")
@Getter
@Setter
@NoArgsConstructor
public class Dictionary_Sentences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionary;

    private Integer idLevel;

    private String sentencesENG;

    private String sentencesPL;

    public Dictionary_Sentences(int idLevel, String sentencesENG, String sentencesPL) {
        this.idLevel = idLevel;
        this.sentencesENG = sentencesENG;
        this.sentencesPL = sentencesPL;
    }


}

