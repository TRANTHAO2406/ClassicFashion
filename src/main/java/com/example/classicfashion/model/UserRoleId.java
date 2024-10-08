package com.example.classicfashion.model;

import java.io.Serializable;
import java.util.Objects;

public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long user;
	private Long role;

	public UserRoleId() {
	}

	public UserRoleId(Long user, Long role) {
		this.user = user;
		this.role = role;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UserRoleId that)) return false;
		return Objects.equals(getUser(), that.getUser()) && Objects.equals(getRole(), that.getRole());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUser(), getRole());
	}
}
