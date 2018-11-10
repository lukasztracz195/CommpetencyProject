package pl.competencyproject.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "FAMILIES")
public class Familie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFamilie;

    private int idLevel;

    private String headFamilie;

    public Familie(){}

    public Familie(int idLevel, String headFamilie) {
        this.idLevel = idLevel;
        this.headFamilie = headFamilie;
    }

    public int getIdFamilie() {
        return idFamilie;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public String getHeadFamilie() {
        return headFamilie;
    }

    public void setHeadFamilie(String headFamilie) {
        this.headFamilie = headFamilie;
    }
}
