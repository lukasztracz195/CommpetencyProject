package pl.competencyproject.model.entities;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name ="USERS")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
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






}