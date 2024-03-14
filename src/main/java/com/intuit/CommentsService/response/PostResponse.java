package com.intuit.CommentsService.response;

import com.intuit.CommentsService.LikeDislikeType;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostResponse {

    private Long postId;
    private String postContent;
    private Timestamp postCreatedTime;

    private Long postUserId;
    private String postUsername;

    private List<CommentResponse> comments;

    private Long likesCount;
    private Long dislikesCount;


    public PostResponse(Post post) {
        this.postId = post.getId();
        this.postContent = post.getContent();
        this.postCreatedTime = post.getPostCreatedTime();

        User user = post.getUser();
        this.postUserId = user.getId();
        this.postUsername = user.getUsername();

        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());

        Long like = post.getLikeCount();

        this.likesCount = post.getLikeCount() == null ? Long.valueOf(0) : like++;

        Long disLike = post.getDislikeCount();

        this.dislikesCount = post.getDislikeCount() == null ? Long.valueOf(0) : disLike++;

        /*this.likesCount = post.getLikesDislikes().stream()
                .filter(likeDislike -> likeDislike.getType() == LikeDislikeType.LIKE)
                .count();

        this.dislikesCount = post.getLikesDislikes().stream()
                .filter(likeDislike -> likeDislike.getType() == LikeDislikeType.DISLIKE)
                .count();*/
    }
}
