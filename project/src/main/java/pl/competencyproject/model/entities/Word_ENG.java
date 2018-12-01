package pl.competencyproject.model.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_ENG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Word_ENG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWordENG;

    private String wordENG;

    public Word_ENG(String wordENG) {
        this.wordENG= wordENG;
    }

}
