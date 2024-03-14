package com.intuit.CommentsService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private Long userId;
    private String userName;
    private String commentContent;
    private Long parentCommentId;
}
