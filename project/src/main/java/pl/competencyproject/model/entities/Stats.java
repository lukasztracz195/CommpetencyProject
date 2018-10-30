package pl.competencyproject.model.entities;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Stats {


    @Column(name = "idStats")
    @Temporal(TemporalType.TIMESTAMP)
    Date dateInput;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idStats")
    private int idStats;
    @Column(name = "idUser")
    private int idUser;
    @Column(name = "idLevel")
    private int idLevel;
    @Column(name = "valueProgres")
    private double valueProgres;

    public Stats() {
    }

    public Stats(int idUser, int idLevel, Date dateInput, double valueProgres) {
        this.idUser = idUser;
        this.idLevel = idLevel;
        this.dateInput = dateInput;
        this.valueProgres = valueProgres;
    }


    public int getIdStats() {
        return idStats;
    }

    public void setIdStats(int idStats) {
        this.idStats = idStats;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdLevel() {
        return idLevel;
    }

    public void setIdLevel(int idLevel) {
        this.idLevel = idLevel;
    }

    public Date getDateInput() {
        return dateInput;
    }

    public void setDateInput(Date dateInput) {
        this.dateInput = dateInput;
    }

    public double getValueProgres() {
        return valueProgres;
    }

    public void setValueProgres(double valueProgres) {
        this.valueProgres = valueProgres;
    }

}