package ru.test.model;

import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by rrv on 15.11.16.
 */

@Entity
@Table(name="user_tab")
public class User
{
    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @NotNull
    @Size(min=1, max=45)
    @Column(name="login")
    private String login;

    @NotEmpty
    @Size(min=1, max=45)
    @Column(name="name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = (login!=null)?login.toUpperCase():null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", login=" + login + "]";
    }
}
