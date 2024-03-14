package com.intuit.CommentsService.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Timestamp commentCreatedTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties("comments")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties("comments")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnoreProperties("replies")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    @OneToMany(mappedBy = "comment")
    @JsonIgnoreProperties("comment")
    private List<LikeDislike> likesDislikes = new ArrayList<>();

    @Column(name = "like_count")
//    @Transient
    private Long likeCount;

    @Column(name = "dislike_count")
//    @Transient
    private Long dislikeCount;

}
