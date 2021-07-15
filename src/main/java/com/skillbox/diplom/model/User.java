package com.skillbox.diplom.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_moderator", nullable = false)
    private boolean isModerator;

    @Column(name = "reg_time", nullable = false)
    private LocalDateTime regTime;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "code")
    private String code;

    @Column(name = "photo")
    private String photo;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Post> postList;
}