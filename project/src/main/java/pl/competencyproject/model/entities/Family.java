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
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFamily;

    private Integer idLevel;

    private String headFamily;

    public Family(Integer idLevel, String headFamily) {
        this.idLevel = idLevel;
        this.headFamily = headFamily;

    }

}
