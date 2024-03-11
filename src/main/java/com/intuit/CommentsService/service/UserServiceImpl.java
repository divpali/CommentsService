package com.intuit.CommentsService.service;

import com.intuit.CommentsService.dto.UserDTO;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    ModelMapper modelMapper;

    public UserServiceImpl() {
        modelMapper = new ModelMapper();
    }


    @Override
    public UserDTO createUser(String username) {
        User user = userRepository.save(User.builder().username(username).build());
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User createUser1(String username) {
        return userRepository.save(User.builder().username(username).build());
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            return null; // or return a default User object, or -1 as per your requirement
        }
    }
}
