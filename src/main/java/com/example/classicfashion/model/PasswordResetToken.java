package com.example.classicfashion.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PASSWORD_RESET_TOKEN")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "used", nullable = false)
    private Boolean used;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, Users user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
        this.used = false;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Users getUser() {
        return user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public Boolean isUsed() {
        return used;
    }
}
