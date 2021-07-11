package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "id_moderator", nullable = false)
	private boolean isModerator;
	
	@Column(name = "reg_time", nullable = false)
	private LocalDateTime regTime;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "code")
	private String code;
	
	@Column(name = "photo")
	private String photo;
}