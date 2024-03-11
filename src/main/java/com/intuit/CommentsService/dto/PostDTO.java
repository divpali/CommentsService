package com.intuit.CommentsService.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long postId;
    private String postContent;
    private Timestamp postCreatedDate;
    private UserDTO userDTO;
    private List<CommentDTO> commentList;

    public void addComments(CommentDTO commentDTO) {
        commentList.add(commentDTO);
        commentDTO.setPostDTO(this);
    }

}
