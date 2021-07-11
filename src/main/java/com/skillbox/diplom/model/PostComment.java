package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_comments")
public class PostComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "parent_id")
	private Integer parentId;
	
	@Column(name = "post_id", nullable = false)
	private int postId;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "text", nullable = false)
	private String text;	
}