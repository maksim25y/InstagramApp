package com.example.demo.services;

import com.example.demo.entity.ImageModel;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.payload.request.ImageCreateRequest;
import com.example.demo.payload.response.ImageResponse;
import com.example.demo.repositories.ImageRepository;
import com.example.demo.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.security.Principal;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public ImageResponse uploadImageToUser(ImageCreateRequest imageCreateRequest, Principal principal) {
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
        var image = imageRepository.save(imageModel);
        return ImageResponse.builder()
                .name(image.getName())
                .encodedImage(image.getEncodedImage())
                .build();
    }

    public ImageResponse uploadImageToPost(ImageCreateRequest imageCreateRequest, Principal principal, Long postId) {
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

        var image = imageRepository.save(imageModel);

        return ImageResponse.builder().name(image.getName()).encodedImage(image.getEncodedImage()).build();
    }

    public ImageResponse getImageToUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        var image = imageRepository.findByUserId(user.getId()).orElse(null);
        if (image != null) {
            return ImageResponse.builder().name(image.getName()).encodedImage(image.getEncodedImage()).build();
        }
        return null;
    }

    public ImageResponse getImageToPost(Long postId) {
        var image = imageRepository.findByPostId(postId)
                .orElseThrow(() -> new ImageNotFoundException(postId));

        return ImageResponse.builder().name(image.getName()).encodedImage(image.getEncodedImage()).build();
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

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