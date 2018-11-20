package pl.competencyproject.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name ="USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String email;

    private String password;

    private boolean active;

    public User(String email, String password, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.active = false;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id: "+this.idUser+" email: "+this.email+" password: "+this.password+" active: "+this.active);
        return sb.toString();
    }





}