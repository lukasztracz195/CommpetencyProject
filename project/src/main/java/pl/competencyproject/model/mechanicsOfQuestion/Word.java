package pl.competencyproject.model.mechanicsOfQuestion;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


//@EqualsAndHashCode(of = {"id"})
@Getter
@ToString
public class Word implements Comparable<Word> {
    private String word;
    private Integer numberOfTries;

    public Word(String word) {
        this.word = word;
        this.numberOfTries = 3;
    }

    @Override
    public int compareTo(Word o) {
        if (this.numberOfTries < o.getNumberOfTries()) {
            return 1;
        } else return -1;
    }

    public void decreasNumberOfAttempts(){
        if(numberOfTries > 0){
            numberOfTries--;
        }
    }
}
