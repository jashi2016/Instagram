package com.kiran.Instagram.service;

import com.kiran.Instagram.model.Post;
import com.kiran.Instagram.model.User;
import com.kiran.Instagram.repository.PostRepository;
import com.kiran.Instagram.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    UserService userService;
    @Autowired
    PostRepository repository;

    @Autowired
    UserRepository UserRepository;
    public String savePost(Post post) {
        User temp = post.getUser();
        Post newPost = null;
        if(UserRepository.findById(temp.getUserId()).isPresent()){
            post.setUser(UserRepository.findById(temp.getUserId()).get());
            newPost = repository.save(post);
            return temp.getFirstName() + " " +
                    temp.getLastName() + " with post id " + newPost.getPostId();
        }
        else return null;
    }

    public JSONArray getPost(Integer postId) {
        JSONArray list = new JSONArray();
        if(postId == null){
            List<Post> postList = repository.findAll();
            for (Post post: postList){
                list.put(this.postToJsonObject(post));
            }
        } else if (repository.findById(postId).isPresent()) {
            Post post = repository.findById(postId).get();
            list.put(this.postToJsonObject(post));
        }
        return list;
    }

    private JSONObject postToJsonObject(Post post){
        JSONObject obj = new JSONObject();

        obj.put("postId", post.getPostId());
        obj.put("postData", post.getPostData());
        obj.put("createdDate", post.getCreatedDate());
        obj.put("updatedDate", post.getUpdatedDate());

        JSONObject objUser = UserService.userToJsonObject(post.getUser());
        obj.put("user", objUser); // whole user given directly

        return obj;
    }

    public String  deletePost(Integer postId) {
        String response = null;
        if(repository.existsById(postId)) {
            Post post = repository.findById(postId).get();
            response = post.getUser().getFirstName() + " " + post.getUser().getFirstName() + " & postId = " + post.getPostId();
            repository.deleteById(postId);
        }
        return response;
    }


    public String updatePost(Post newPost,Integer postId) {
        String response = null;
        if(repository.existsById(postId)) {
            Post oldPost = repository.findById(postId).get();
            oldPost.setPostData(newPost.getPostData());
            oldPost.setUpdatedDate((newPost.getUpdatedDate()));
            response = oldPost.getUser().getFirstName() + " " + oldPost.getUser().getFirstName() + " & postId = " + postId;
            repository.save(oldPost);
        }
        return response;
    }
}