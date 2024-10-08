package com.example.classicfashion.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "TOKEN")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "token")
    private String token;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "expiryDate")
    private LocalDate expiryDate;

    public Token() {
    }

    public Token(Long id, String token, User user, LocalDate expiryDate) {
        Id = id;
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
}
