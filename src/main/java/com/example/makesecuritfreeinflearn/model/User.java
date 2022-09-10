package com.example.makesecuritfreeinflearn.model;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

// ORM - Object Relation Mapping

@Data
@Entity
public class User {
	@Id // primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;   // "google_113772075971461623885"
	private String password;   // "암호화(겟인데어)"
	private String email;      // "mygoaway2@gmail.com"
	private String role;       // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN
	private String provider;   // "google"
	private String providerId; // "113772075971461623885"
	@CreationTimestamp
	private Timestamp createDate;

	public User() {
	}

	@Builder
	public User(String username, String password, String email, String role, String provider, String providerId) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
	}
}
