package com.intuit.CommentsService.response;

import com.intuit.CommentsService.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String userName;

    public UserResponse(User user) {
        this.userId = user.getId();
        this.userName = user.getUsername();
    }
}
