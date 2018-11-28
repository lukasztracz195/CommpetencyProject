package pl.competencyproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "LEVELS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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


}
