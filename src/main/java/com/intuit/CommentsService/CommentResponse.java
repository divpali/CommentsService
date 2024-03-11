package com.intuit.CommentsService;

import com.intuit.CommentsService.dto.PostDTO;
import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.Post;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class CommentResponse {

    private Long commentId;
    private String commentContent;
    private Timestamp commentCreatedTime;
    private Long postId;
    private String postContent;
    private Long userId;
    private String userName;

}
