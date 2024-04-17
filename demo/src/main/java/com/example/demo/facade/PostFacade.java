package com.example.demo.facade;

import com.example.demo.dto.PostDTO;
import com.example.demo.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostFacade {
    public PostDTO postToPostDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postDTO.getId());
        postDTO.setUsername(post.getUser().getUsername());
        postDTO.setLikes(post.getLikes());
        postDTO.setTitle(postDTO.getTitle());
        postDTO.setLocation(post.getLocation());
        postDTO.setLikes(post.getLikes());
        postDTO.setUsersLiked(post.getLikedUsers());
        return postDTO;
    }
}
