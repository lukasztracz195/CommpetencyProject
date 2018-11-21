package pl.competencyproject.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DICTIONARY_WORDS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dictionary_Words {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionaryWords;

    private Integer idLevel;

    private Integer idFamily;

    private Integer idWordENG;

    private Integer idWordPL;


    public Dictionary_Words(Integer idLevel, Integer idFamilie, Integer idWordENG, Integer idWordPL) {
        this.idLevel = idLevel;
        this.idFamily = idFamilie;
        this.idWordENG = idWordENG;
        this.idWordPL = idWordPL;
    }
}


