package com.intuit.CommentsService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long postId;
    private UserDto userDto;
    private String content;
    private long post_created_date;
    private List<CommentDto> commentDtoList;

}
