package pl.competencyproject.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_PL")
@Getter
@Setter
@NoArgsConstructor
public class Word_PL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWordPL;

    private String wordPL;

    public Word_PL(String wordPL) {
        this.wordPL = wordPL;
    }

}
