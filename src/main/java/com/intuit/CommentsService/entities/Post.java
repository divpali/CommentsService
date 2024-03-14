package com.intuit.CommentsService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Timestamp postCreatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("user")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnoreProperties("post")
    private List<LikeDislike> likesDislikes = new ArrayList<>();

    @Column(name = "like_count")
    @Transient
    private Long likeCount;

    @Column(name = "dislike_count")
    @Transient
    private Long dislikeCount;

}
