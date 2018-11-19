package pl.competencyproject.model.entities;

import org.hibernate.HibernateException;

import javax.persistence.*;

@Entity
@Table(name = "LEVELS")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLevel;

    private String nameLevel;


    private String nameCategorie;

    public Level(String levelName, String categorie) {
        this.nameLevel = levelName;
        this.nameCategorie = categorie;
    }

    public Level() {
    }

    public int getIdLevel() {
        return idLevel;
    }

    public String getLevelName() {
        return nameLevel;
    }

    public String getCategorie() {
        return nameCategorie;
    }

    public  void deleteLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public void setLevelName(String levelName) {
        this.nameLevel = levelName;
    }

    public void setCategorie(String categorie) {
        this.nameCategorie = categorie;
    }


    public  int addLevel(String nameLevel, String nameCategorie) {
        return idLevel;
    }

}
