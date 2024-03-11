package com.intuit.CommentsService.response;

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

    private Long userId;
    private String username;

    private List<CommentResponse> comments;

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.postContent = post.getContent();
        this.postCreatedTime = post.getPostCreatedTime();

        User user = post.getUser();
        this.userId = user.getId();
        this.username = user.getUsername();

        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
