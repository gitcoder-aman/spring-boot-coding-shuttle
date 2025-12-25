package com.tech.Module4.services;

import com.tech.Module4.dto.PostDTO;
import com.tech.Module4.entities.PostEntity;
import com.tech.Module4.exceptions.ResourceNotFoundException;
import com.tech.Module4.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        PostEntity postEntity = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post not found with id= "+postId));
        return modelMapper.map(postEntity,PostDTO.class);
    }
}
