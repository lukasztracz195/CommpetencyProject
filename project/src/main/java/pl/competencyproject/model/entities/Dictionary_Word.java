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
public class Dictionary_Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDictionaryWords;

    private Integer idLevel;

    private Integer idFamily;

    private Integer idWordENG;

    private Integer idWordPL;


    public Dictionary_Word(Integer idLevel, Integer idFamily, Integer idWordENG, Integer idWordPL) {
        this.idLevel = idLevel;
        this.idFamily = idFamily;
        this.idWordENG = idWordENG;
        this.idWordPL = idWordPL;
    }
}


