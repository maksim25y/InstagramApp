package com.example.demo.services;

import com.example.demo.entity.ImageModel;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.payload.request.ImageCreateRequest;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ImageUploadService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageUploadService.class);

    private ImageRepository imageRepository;
    private UserRepository userRepository;

    @Autowired
    public ImageUploadService(ImageRepository imageRepository, UserRepository userRepository) {
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public ImageModel uploadImageToUser(ImageCreateRequest imageCreateRequest, Principal principal) {
        User user = getUserByPrincipal(principal);
        LOG.info("Uploading image profile to User {}", user.getUsername());

        ImageModel userProfileImage = imageRepository.findByUserId(user.getId()).orElse(null);
        if (!ObjectUtils.isEmpty(userProfileImage)) {
            imageRepository.delete(userProfileImage);
        }

        ImageModel imageModel = new ImageModel();
        imageModel.setUserId(user.getId());
        imageModel.setEncodedImage(imageCreateRequest.getEncodedImage());
        imageModel.setName(imageCreateRequest.getName());
        return imageRepository.save(imageModel);
    }

    public ImageModel uploadImageToPost(ImageCreateRequest imageCreateRequest, Principal principal, Long postId) {
        User user = getUserByPrincipal(principal);
        Post post = user.getPosts()
                .stream()
                .filter(p -> p.getId().equals(postId))
                .collect(toSinglePostCollector());

        ImageModel imageModel = new ImageModel();
        imageModel.setPostId(post.getId());
        imageModel.setEncodedImage(imageCreateRequest.getEncodedImage());
        imageModel.setName(imageCreateRequest.getName());
        LOG.info("Uploading image to Post {}", post.getId());

        return imageRepository.save(imageModel);
    }

    public ImageModel getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return imageRepository.findByUserId(user.getId()).orElse(null);
    }

    public ImageModel getImageToPost(Long postId) {
        return imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post: " + postId));
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));

    }

    private <T> Collector<T, ?, T> toSinglePostCollector() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}