package com.siva.edairy.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	@Column(name="fullname", length=40, nullable = false)
	private String fullname;
	@Column(name="email", length=40, nullable=false, unique=true)
	private String email;
	@Column(name="mobile", length=10, nullable = false, unique=true)
	private String mobile;
	@Column(name="password", length=20, nullable = false)
	private String password;
	@Column(name="dob", nullable = false)
	private Date dateOfBirth;
	@Column(name="address", length=80, nullable=false)
	private String address;

}
