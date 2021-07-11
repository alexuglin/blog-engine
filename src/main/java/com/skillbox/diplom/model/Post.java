package com.skillbox.diplom.model;

import com.skillbox.diplom.model.enums.ModerationStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "moderation_status", columnDefinition = "enum", nullable = false)
	private ModerationStatus moderationStatus;
	
	@Column(name = "moderator_id")
	private Integer moderatorId;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@Column(name = "view_count", nullable = false)
	private int viewCount;
}
