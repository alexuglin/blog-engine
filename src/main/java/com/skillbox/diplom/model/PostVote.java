package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_votes")
public class PostVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@Column(name = "post_id", nullable = false)
	private int postId;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "value", nullable = false)
	private byte value;	
}