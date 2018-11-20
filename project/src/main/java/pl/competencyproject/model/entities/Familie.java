package pl.competencyproject.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "FAMILIES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Familie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFamilie;

    private Integer idLevel;

    private String headFamilie;

    public Familie(Integer idLevel, String headFamilie) {
        this.idLevel = idLevel;
        this.headFamilie = headFamilie;

    }

}
