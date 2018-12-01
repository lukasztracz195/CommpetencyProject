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
    @Column(name = "idStat")
    private Integer idStat;

    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "idLevel")
    private Integer idLevel;

    @Column(name = "dateInput")
    private Date dateInput;

    @Column(name = "valueProgress")
    private Double valueProgress;

    public Stat(int idUser, int idLevel, double valueProgress) {
        this.idUser = idUser;
        this.idLevel = idLevel;
        this.valueProgress = valueProgress;
        this.dateInput = new Date();
    }
}