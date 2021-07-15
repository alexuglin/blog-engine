package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comments")
public class PostComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "parent_id")
	private Integer parentId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "text", nullable = false)
	private String text;	
}