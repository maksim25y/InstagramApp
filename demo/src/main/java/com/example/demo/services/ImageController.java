package com.example.demo.services;

import com.example.demo.entity.ImageModel;
import com.example.demo.payload.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
public class ImageController {
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse>uploadImageToUser(@RequestParam("file")MultipartFile file, Principal principal)throws IOException {
        imageUploadService.uploadImageToUser(file,principal);
        return new ResponseEntity<>(new MessageResponse("Image uploaded Successfully"), HttpStatus.OK);
    }
    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse>uploadImageToPost(@PathVariable("postId")String postId,
                                                            @RequestParam("file")MultipartFile file,
                                                            Principal principal) throws IOException{
        imageUploadService.uploadImageToPost(file,principal,Long.parseLong(postId));
        return new ResponseEntity<>(new MessageResponse("Image uploaded Succesfully"),HttpStatus.OK);
    }
    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel>getImageForUser(Principal principal){
        ImageModel userImage = imageUploadService.getImageToUser(principal);
        return new ResponseEntity<>(userImage,HttpStatus.OK);
    }
    @GetMapping("/{postId}/image")
    public ResponseEntity<ImageModel>getImageForUser(@PathVariable("postId")String postId){
        ImageModel postImage = imageUploadService.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(postImage,HttpStatus.OK);
    }
}
