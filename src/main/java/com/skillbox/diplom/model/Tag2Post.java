package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tag2post")
public class Tag2Post {
		
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "tag_id", nullable = false)
	private Tag tag;
}