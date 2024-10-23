package com.example.classicfashion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email(message = "Email phải đúng định dạng.")
	@NotBlank(message = "Email không được để trống.")
	@Column(nullable = false, unique = true)
	private String email;

	@NotBlank(message = "Mật khẩu không được để trống.")
//	@Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự.")
//	@Pattern(regexp = ".*[A-Z].*", message = "Mật khẩu phải có ít nhất 1 ký tự viết hoa.")
	@Column(nullable = false)
	private String password;

	@NotBlank(message = "Tên người dùng không được để trống.")
	@Column(name = "userName", nullable = false, columnDefinition = "NVARCHAR(255)")
	private String userName;

	@NotBlank(message = "Số điện thoại không được để trống.")
	@Pattern(regexp = "\\d{10}", message = "Số điện thoại phải có 10 chữ số.")
	@Column(nullable = false, length = 10)
	private String phone;

	@NotBlank(message = "Địa chỉ không được để trống.")
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	private String address;

	@Column(columnDefinition = "NVARCHAR(255)")
	private String status;

	@Column(name = "createdDate", nullable = false)
	private LocalDate createdDate;

	@Column(name = "updatedDate")
	private LocalDate updatedDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PasswordResetToken> passwordResetTokens;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<UserRole> userRoles = new ArrayList<>();  // Khởi tạo danh sách userRoles

	// Constructor đầy đủ tham số
	public Users(Long id, String email, String password, String userName, String phone, String address, String status,
				 LocalDate createdDate, LocalDate updatedDate) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.userName = userName;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.userRoles = new ArrayList<>();  // Khởi tạo danh sách userRoles trong constructor có tham số
	}

	// Constructor mặc định
	public Users() {
		this.userRoles = new ArrayList<>();  // Khởi tạo danh sách userRoles trong constructor mặc định
	}

	// Getters và Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", userName='" + userName + '\'' +
				", phone='" + phone + '\'' +
				", address='" + address + '\'' +
				", status='" + status + '\'' +
				", createdDate=" + createdDate +
				", updatedDate=" + updatedDate +
				", userRoles=" + userRoles +
				'}';
	}
}
