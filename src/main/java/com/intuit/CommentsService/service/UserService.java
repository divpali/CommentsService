package com.intuit.CommentsService.service;

import com.intuit.CommentsService.entities.User;

import java.util.List;

public interface UserService {

    public User getUserById(long id);

    public User save(String userName);

    public List<User> getAllUser();

}
