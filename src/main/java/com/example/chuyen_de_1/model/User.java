package com.example.chuyen_de_1.model;


import jakarta.persistence.*;

@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false, length = 255)
    private String username;
    private String password;

    public User() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
