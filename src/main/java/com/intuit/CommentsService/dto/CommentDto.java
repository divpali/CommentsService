package com.intuit.CommentsService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long comment_id;
    private String text;
    private UserDto userDto;
    private PostDto postDto;
    private CommentDto parentComment; //for nesting
    private long comment_created_date;
    private List<CommentDto> replies;

    public void addReply(CommentDto commentDto) {
        replies.add(commentDto);
    }
}
