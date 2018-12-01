package pl.competencyproject.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_PL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Word_PL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWordPL;

    private String wordPL;

    public Word_PL(String wordPL) {
        this.wordPL = wordPL;
    }

}
