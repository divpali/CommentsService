package com.intuit.CommentsService.response;

import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.User;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentResponse {

    private Long commentId;
    private String commentContent;
    private Timestamp commentCreatedTime;

    private Long userId;
    private String username;
    private List<CommentResponse> replies; // Updated to handle replies

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.commentContent = comment.getContent();
        this.commentCreatedTime = comment.getCommentCreatedTime();

        User user = comment.getUser();
        this.userId = user.getId();
        this.username = user.getUsername();

        this.replies = comment.getReplies().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
