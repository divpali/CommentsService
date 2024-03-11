package com.intuit.CommentsService.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Long commentId;
    private String commentContent;
    private Timestamp commentCreatedTime;
    private UserDTO userDTO;
    private PostDTO postDTO;
    private Long parentCommentId;
    private List<CommentDTO> nestedComments;

}
