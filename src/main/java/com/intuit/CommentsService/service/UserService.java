package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.User;

import java.util.List;

public interface UserService {

    UserDTO createUser(String username);

    User createUser1(String username);

    User getUserById(Long userId);
}
