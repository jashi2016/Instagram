package com.kiran.Instagram.controller;

import com.kiran.Instagram.model.Post;
import com.kiran.Instagram.repository.UserRepository;
import com.kiran.Instagram.service.PostService;
import com.kiran.Instagram.util.Util;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/post")
public class PostController {
    @Autowired
    PostService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Util util;
    @PostMapping("save") // trying with sending userID to from controller but still showing null...
    private ResponseEntity<String> savePost(@RequestBody String newPost) {
        Post post = util.setPost(newPost);
        String name = service.savePost(post); // name = name + " with post id " + postId
        if (name != null)
            return new ResponseEntity<>("Post of User Name " + name + " saved!", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("User not found plz create user first", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get")
    private ResponseEntity<String> getPost(@Nullable @RequestParam Integer postId){
        JSONArray post = service.getPost(postId);
        if(post.isEmpty()) return new ResponseEntity<>("Requested Posts Doesn't exist!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(post.toString(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete")
    private ResponseEntity<String> deletePost(@Nullable @RequestParam Integer postId) {
        String response = service.deletePost(postId);
        if(response == null) return new ResponseEntity<>("Requested Post Doesn't exist!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("Post of User Name " + response + " deleted!", HttpStatus.ACCEPTED);

    }

    @PutMapping("update")
    private ResponseEntity<String> updatePost(@RequestBody String newPost,@RequestParam Integer postId){
        Post post = util.setPost(newPost);
        String response = service.updatePost(post, postId);
        if(response == null) return new ResponseEntity<>("Requested Post Doesn't exist!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("Post of User Name " + response + " update!", HttpStatus.ACCEPTED);
    }

}