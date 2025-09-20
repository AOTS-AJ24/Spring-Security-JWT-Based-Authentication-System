package com.abhi.SpringSecEx.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer_Login_Details")
public class LoginData {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to auto increment
    @Column(nullable = false)
    private Integer id;
    private String username;
    private String password;
    private String roles ;

    public LoginData() {
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }



    public LoginData(Integer id, String password, String username ,String roles) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "LoginData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
