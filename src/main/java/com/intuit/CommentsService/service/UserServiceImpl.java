package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.UserDto;
import com.intuit.CommentsService.entities.Comment;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper;

    public UserServiceImpl() {
        modelMapper = new ModelMapper();
    }

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public User save(String userName) {
        User user = User.builder().username(userName).build();
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
