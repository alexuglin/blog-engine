package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tags")
public class Tag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "tag2post",
			joinColumns = {@JoinColumn (name = "tag_id")},
			inverseJoinColumns = {@JoinColumn(name = "post_id")})
	private List<Post> postList;
}