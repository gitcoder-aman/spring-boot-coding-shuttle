package com.tech.module5.SecurityApplication.util;

import com.tech.module5.SecurityApplication.dto.PostDTO;
import com.tech.module5.SecurityApplication.entities.PostEntity;
import com.tech.module5.SecurityApplication.entities.UserApp;
import com.tech.module5.SecurityApplication.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;
   public boolean isOwnerOfPost(Long postId){
        UserApp user = (UserApp) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        PostDTO post = postService.getPostById(postId);
        return post.getAuthor().getId().equals(Objects.requireNonNull(user).getId());
    }
}
