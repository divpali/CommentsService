package com.intuit.CommentsService.controller;


import com.intuit.CommentsService.dto.PostDTO;
import com.intuit.CommentsService.entities.Post;
import com.intuit.CommentsService.entities.User;
import com.intuit.CommentsService.service.PostService;
import com.intuit.CommentsService.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments-service1")
public class PhaseController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    ModelMapper modelMapper;

    PhaseController() {
        modelMapper = new ModelMapper();
    }


}
