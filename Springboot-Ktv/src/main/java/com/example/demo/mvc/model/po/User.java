package com.example.demo.mvc.model.po;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	
	private String userName;
	private String gender;
	private String email;
	private String password;
	private String phone;
	private String salt;
	private Integer permissions;

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", gender='" + gender + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", salt='" + salt + '\'' +
				", permissions=" + permissions +
				'}';
	}
	
	// 添加構造函數
    public User(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}