package pl.competencyproject.model.entities;

import javax.persistence.*;
@Entity
@Table(name ="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "idUser")
    private int idUser;

   // @Column(name = "email")
    private String email;

   // @Column(name = "password")
    private String password;

    //@Column(name = "active")
    private boolean active;

    public User(String email, String password, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
    }

    public User() {
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }





}