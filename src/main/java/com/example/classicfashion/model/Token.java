package com.example.classicfashion.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "TOKEN")
public class Token {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Getter
    @Column(name = "token")
    private String token;

    @Getter
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users user;

    @Getter
    @Column(name = "createdDate")
    private LocalDateTime createdDate;

    @Getter
    @Column(name = "expiryDate")
    private LocalDateTime expiryDate;

    @Column(name = "isUsed")
    private boolean isUsed;

    public Token() {
    }

    public Token(String token, Users user, LocalDateTime createdDate, LocalDateTime expiryDate, boolean isUsed) {
        this.token = token;
        this.user = user;
        this.createdDate = createdDate;
        this.expiryDate = expiryDate;
        this.isUsed = isUsed;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean getUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

	public Long getId() {
		return Id;
	}

	public String getToken() {
		return token;
	}

	public Users getUser() {
		return user;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	
}
