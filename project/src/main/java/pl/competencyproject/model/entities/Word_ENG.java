package pl.competencyproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "WORDS_ENG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word_ENG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idWordENG;

    private String wordENG;

    public Word_ENG(String wordENG) {
        this.wordENG= wordENG;
    }

}
