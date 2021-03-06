package pl.competencyproject.model.pollingMechanizm;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@ToString
public class Word implements Comparable<Word> {
    private String word;
    private Integer numberOfTries;

    public Word(String word) {
        this.word = word;
        this.numberOfTries = 3;
    }

    public Word(Word tmp){
        this.numberOfTries = tmp.getNumberOfTries();
        this.word = tmp.getWord();
    }

    @Override
    public int compareTo(Word o) {
        String sThis = ((char) (this.getNumberOfTries() + 65)) + ' ' + this.getWord();
        String sOther = ((char) (o.getNumberOfTries() + 65)) + ' ' + o.getWord();
        return (sThis.compareTo(sOther)) * (-1);
    }

    public void decreasNumberOfAttempts() {
        if (numberOfTries > 0) {
            numberOfTries--;
        }
    }

    public String getKeyValue() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getWord() + this.getKeyValue());
        return sb.toString();
    }
}
