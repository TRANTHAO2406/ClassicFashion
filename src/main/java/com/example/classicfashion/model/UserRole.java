package com.example.classicfashion.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERROLE")
@IdClass(UserRoleId.class)
public class UserRole {

	@Id
	@ManyToOne
	@JoinColumn(name = "userId")
	private Users user;

	@Id
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	public UserRole() {
	}

	public UserRole(Users user, Role role) {
		this.user = user;
		this.role = role;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserRole userRole)) return false;
		return Objects.equals(getUser(), userRole.getUser()) && Objects.equals(getRole(), userRole.getRole());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUser(), getRole());
	}
}
