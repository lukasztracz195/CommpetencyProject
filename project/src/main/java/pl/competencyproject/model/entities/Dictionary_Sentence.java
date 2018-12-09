package pl.competencyproject.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_SENTENCES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dictionary_Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionary;

    private Integer idLevel;

    private String sentencesENG;

    private String sentencesPL;

    public Dictionary_Sentence(int idLevel, String sentencesENG, String sentencesPL) {
        this.idLevel = idLevel;
        this.sentencesENG = sentencesENG;
        this.sentencesPL = sentencesPL;
    }
}

