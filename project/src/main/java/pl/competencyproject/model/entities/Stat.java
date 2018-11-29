package pl.competencyproject.model.entities;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STATS")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Stat {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer idStat;

    private Integer idUser;

    private Integer idLevel;

    private Date dateInput;

    private Double valueProgress;

    public Stat(int idUser, int idLevel, double valueProgress) {
        this.idUser = idUser;
        this.idLevel = idLevel;
        this.valueProgress = valueProgress;
        this.dateInput = new Date();
    }
}