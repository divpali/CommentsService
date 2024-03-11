package com.intuit.CommentsService;

import lombok.Data;

@Data
public class CommentRequest {

    private String commentContent;
    private String userName;
    private Long userId;
}
