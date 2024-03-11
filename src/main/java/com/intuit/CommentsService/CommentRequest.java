package com.intuit.CommentsService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {

    private Long id;
    private String userName;
    private String content;
    private Long parentCommentId;
}
