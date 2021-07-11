package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "global_settings")
public class GlobalSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "value", nullable = false)
	private String value;		
}