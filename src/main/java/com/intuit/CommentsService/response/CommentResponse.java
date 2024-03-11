package com.intuit.CommentsService.response;

import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.User;
import lombok.Data;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CommentResponse {

    private Long commentId;
    private String commentContent;
    private Timestamp commentCreatedTime;

    private Long userId;
    private String username;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.commentContent = comment.getContent();
        this.commentCreatedTime = comment.getCommentCreatedTime();

        User user = comment.getUser();
        this.userId = user.getId();
        this.username = user.getUsername();
    }
}
