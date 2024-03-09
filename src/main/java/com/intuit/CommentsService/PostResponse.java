package com.intuit.CommentsService;

import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.intuit.CommentsService.dto.CommentDto;
import com.intuit.CommentsService.dto.UserDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private long postId;
    private UserDto userDto;
    private String content;
    private long postCreatedDate;
    private List<CommentDto> commentDtoList;

    public static PostResponse fromEntity(PostDto post) {
        UserDto userDto = post.getUserDto();

        return PostResponse.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .postCreatedDate(post.getPost_created_date())
                .userDto(userDto)
                .build();
    }
}
