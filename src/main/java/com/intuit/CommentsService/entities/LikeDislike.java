package com.intuit.CommentsService.entities;

import com.intuit.CommentsService.enums.LikeDislikeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LIKESDISLIKES")
public class LikeDislike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_DISLIKE_ID")
    private Long likeDislikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private LikeDislikeType type;

    @Column(name = "TIMESTAMP")
    private long likeDislikeTimeStamp;

    @Column(name = "LIKE_COUNT")
    private int likeCount;

    @Column(name = "DISLIKE_COUNT")
    private int dislikeCount;

}
