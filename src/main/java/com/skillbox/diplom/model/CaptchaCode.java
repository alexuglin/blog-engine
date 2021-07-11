package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "captcha_codes")
public class CaptchaCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "code", nullable = false)
	private byte code;
	
	@Column(name = "secret_code", nullable = false)
	private byte secretCode;
}