package com.intuit.CommentsService.dto;

import com.intuit.CommentsService.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long userId;
    private String userName;

}
