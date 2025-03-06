package com.example.demo.services;

import com.example.demo.facade.CommentFacade;
import com.example.demo.payload.request.CommentCreateRequest;
import com.example.demo.payload.response.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.PostNotFoundException;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    public static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentFacade commentFacade;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, CommentFacade commentFacade) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentFacade = commentFacade;
    }

    public CommentDTO saveComment(Long postId, CommentCreateRequest commentCreateRequest, Principal principal) {
        var user = getUserByPrincipal(principal);
        var post = postRepository.findById(postId)//TODO
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found for username: " + user.getEmail()));

        var comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentCreateRequest.getMessage());

        LOG.info("Saving comment for Post: {}", post.getId());
        return commentFacade.commentToCommentDTO(commentRepository.save(comment));
    }

    public List<CommentDTO> getAllCommentsForPost(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post cannot be found"));//TODO

        return commentRepository.findAllByPost(post).stream()
                .map(commentFacade::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId) {
        var comment = commentRepository.findById(commentId)
                .orElseThrow();//TODO
        commentRepository.delete(comment);
    }


    private User getUserByPrincipal(Principal principal) {
        var username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }
}
