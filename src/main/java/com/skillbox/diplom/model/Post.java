package com.skillbox.diplom.model;

import com.skillbox.diplom.model.enums.ModerationStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "is_active", nullable = false)
	private boolean isActive;
	
	@Enumerated(value = EnumType.STRING)
	@Column(name = "moderation_status", columnDefinition = "enum", nullable = false)
	private ModerationStatus moderationStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moderator_id")
	private User moderator;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "time", nullable = false)
	private LocalDateTime time;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@OneToMany(mappedBy = "post", cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private List<PostVote> postVoteList;

	@OneToMany(mappedBy = "post", cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	private List<PostComment> postComments;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinTable(name = "tag2post",
		joinColumns = {@JoinColumn (name = "post_id")},
	inverseJoinColumns = {@JoinColumn(name = "tag_id")})
	private List<Tag> tagList;
}
