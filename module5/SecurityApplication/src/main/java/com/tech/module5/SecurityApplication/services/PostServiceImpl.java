package com.tech.module5.SecurityApplication.services;

import com.tech.module5.SecurityApplication.dto.PostDTO;
import com.tech.module5.SecurityApplication.entities.PostEntity;
import com.tech.module5.SecurityApplication.entities.UserApp;
import com.tech.module5.SecurityApplication.exceptions.ResourceNotFoundException;
import com.tech.module5.SecurityApplication.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public List<PostDTO> getAllPost() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity,PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO inputPost) {
        PostEntity postEntity = modelMapper.map(inputPost,PostEntity.class);
        PostEntity savedPost = postRepository.save(postEntity);
       return modelMapper.map(savedPost,PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        UserApp user = (UserApp) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        log.info("User {}",user);
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id= "+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity olderPost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with Id="+postId));
        inputPost.setId(postId);
        modelMapper.map(inputPost,olderPost);
        PostEntity savePostEntity = postRepository.save(olderPost);
        return modelMapper.map(savePostEntity,PostDTO.class);
    }
}
