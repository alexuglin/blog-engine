package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tags")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
}