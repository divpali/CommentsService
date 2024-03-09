package com.intuit.CommentsService;

import com.intuit.CommentsService.dto.CommentDto;
import com.intuit.CommentsService.dto.PostDto;
import com.intuit.CommentsService.dto.UserDto;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    private long comment_id;
    private String text;
    private UserDto userDto;
    private String post;
    private CommentDto parentComment;
    private long comment_created_date;
    private List<CommentDto> replies;

    public static CommentResponse fromEntity(Comment comment, UserDto userDto, PostDto postDto) {

        return CommentResponse.builder()
                .comment_id(comment.getCommentId())
                .text(comment.getText())
                .comment_created_date(comment.getComment_created_date().getTime())
                .userDto(userDto)
                .post(postDto.getContent())
                .build();

    }

}
