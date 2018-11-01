package pl.competencyproject.model.entities;

import pl.competencyproject.model.DAO.ManageUsers;

import javax.persistence.*;
@Entity
@Table(name ="USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    private String email;

    private String hash;

    private String password;

    private boolean active;

    public User(String email, String password,String hash, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.hash = hash;
    }

    public User(String email, String password, boolean active) {
        this.email = email;
        this.password = password;
        this.active = active;
        this.hash = null;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.active = false;
        this.hash = null;
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

    public String getHash() { return hash; }

    public void setHash(String hash) { this.hash = hash;}

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