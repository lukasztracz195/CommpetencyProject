package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name ="EXAMPLES")
public class Example {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int idExample;


    private int idTranslation;

    private String engExample;

    private String plExample;

    public Example(int idTranslation, String engExample, String plExample) {
        this.idTranslation = idTranslation;
        this.engExample = engExample;
        this.plExample = plExample;
    }

    public Example() {
    }

    public int getIdExample() {
        return idExample;
    }

    public int getIdTranslation() {
        return idTranslation;
    }

    public String getEngExample() {
        return engExample;
    }

    public String getPlExample() {
        return plExample;
    }
}
