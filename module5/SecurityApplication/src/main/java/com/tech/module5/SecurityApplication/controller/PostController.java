package com.tech.module5.SecurityApplication.controller;

import com.tech.module5.SecurityApplication.dto.PostDTO;
import com.tech.module5.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDTO> getPosts(){
        return postService.getAllPost();
    }

    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost){
        return postService.createNewPost(inputPost);
    }

    @PutMapping("{postId}")
    public PostDTO updatedPost(@RequestBody PostDTO inputPost,@PathVariable Long postId){
        return postService.updatePost(inputPost,postId);
    }

    @GetMapping("{postId}")
    public PostDTO getPostById(@PathVariable Long postId){
        return postService.getPostById(postId);
    }
}
