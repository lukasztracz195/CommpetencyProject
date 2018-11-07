package pl.competencyproject.model.entities;

import javax.persistence.*;
@Entity
@Table(name ="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

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

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("id: "+this.idUser+" email: "+this.email+" password: "+this.password+" active: "+this.active);
        return sb.toString();
    }





}