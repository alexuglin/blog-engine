package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "post_votes")
public class PostVote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "user_id")
	private int userId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name = "post_id")
	private Post post;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "value", nullable = false)
	private byte value;	
}