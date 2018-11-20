package pl.competencyproject.model.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STATS")
@Getter
@Setter
@NoArgsConstructor
public class Stat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idStats;

    private Integer idUser;

    private Integer idLevel;

    @Temporal(TemporalType.TIMESTAMP)
    Date dateInput;

    private Double valueProgress;

    public Stat(Integer idUser, Integer idLevel, Double valueProgress) {
        this.idUser = idUser;
        this.idLevel = idLevel;
        this.dateInput = new Date();
        this.valueProgress = valueProgress;
    }
}