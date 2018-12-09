package pl.competencyproject.model.DW;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = {"id"})
public class Word implements Comparable<Word> {
    String word;
    Integer id;
    Integer numberOfTries;

    public Word(Integer id, String word) {
        this.id = id;
        this.word = word;
        this.numberOfTries = 3;
    }

    @Override
    public int compareTo(Word o) {
        if (this.numberOfTries <= o.getNumberOfTries()) {
            return 1;
        } else return -1;
    }
}
