package com.intuit.CommentsService;

import lombok.Data;

@Data
public class PostRequest {

    private String postContent;
    private Long userId;
}
