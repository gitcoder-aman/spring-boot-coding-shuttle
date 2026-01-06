package com.tech.module5.SecurityApplication.services;


import com.tech.module5.SecurityApplication.dto.PostDTO;

import java.util.List;

public interface PostService {

    List<PostDTO>getAllPost();

    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long postId);

    PostDTO updatePost(PostDTO inputPost, Long postId);
}
