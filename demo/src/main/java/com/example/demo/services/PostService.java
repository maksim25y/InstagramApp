package com.example.demo.services;

import com.example.demo.mapper.PostMapper;
import com.example.demo.payload.request.PostCreateRequest;
import com.example.demo.payload.response.PostDTO;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final PostMapper postMapper;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.postMapper = postMapper;
    }

    public PostDTO createPost(PostCreateRequest postCreateRequest, Principal principal) {
        var user = getUserByPrincipal(principal);

        var post = new Post();
        post.setUser(user);
        post.setTitle(postCreateRequest.getTitle());
        post.setCaption(postCreateRequest.getCaption());
        post.setLocation(postCreateRequest.getLocation());
        post.setLikes(0);

        LOG.info("Saving Post for User:{}", user.getEmail());
        return postMapper.postToPostDTO(postRepository.save(post));
    }

    public List<PostDTO> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDateDesc().stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList());
    }

    public Post getPostById(Long postId, Principal principal) {
        var user = getUserByPrincipal(principal);

        return postRepository.findPostByIdAndUser(postId, user)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public List<PostDTO> getAllPostsForUser(Principal principal) {
        var user = getUserByPrincipal(principal);

        return postRepository.findAllByUserOrderByCreatedDateDesc(user)
                .stream()
                .map(postMapper::postToPostDTO)
                .collect(Collectors.toList());
    }

    public PostDTO likePost(Long postId, String username) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        var userLiked = post.getLikedUsers()
                .stream()
                .filter(u -> u.equals(username)).findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }
        return postMapper.postToPostDTO(postRepository.save(post));
    }

    public void deletePost(Long postId, Principal principal) {
        var post = getPostById(postId, principal);
        var imageModel = imageRepository.findByPostId(post.getId());

        imageModel.ifPresent(imageRepository::delete);
        postRepository.delete(post);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
    }

}
