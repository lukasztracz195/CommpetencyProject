package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name ="DEFINITIONS")
public class Definition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDefinitions;

    private int idTranslation;

    private String plDefinition;

    private String angDefinition;

    public Definition(int idTranslation, String plDefinition, String angDefinition) {
        this.idTranslation = idTranslation;
        this.plDefinition = plDefinition;
        this.angDefinition = angDefinition;
    }

    public Definition() {
    }

    public int getIdDefinitions() {
        return idDefinitions;
    }

    public int getIdTranslation() {
        return idTranslation;
    }

    public String getPlDefinition() {
        return plDefinition;
    }

    public String getAngDefinition() {
        return angDefinition;
    }
}
