package com.kiran.Instagram.util;

import com.kiran.Instagram.model.Post;
import com.kiran.Instagram.model.User;
import com.kiran.Instagram.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Util {
    public User setUser(String user) {
        JSONObject jsonObject = new JSONObject(user);
        //validations before saving
        User newUser = new User();

        if(jsonObject.has("firstName"))
            newUser.setFirstName(jsonObject.getString("firstName"));
        if(jsonObject.has("lastName"))
            newUser.setLastName(jsonObject.getString("lastName"));
        if(jsonObject.has("age"))
            newUser.setAge(jsonObject.getInt("age"));
        if(jsonObject.has("phoneNumber"))
            newUser.setPhoneNumber(jsonObject.getString("phoneNumber"));
        if(jsonObject.has("email"))
            newUser.setEmail(jsonObject.getString("email"));
        if(jsonObject.has("userId"))
            newUser.setUserId(jsonObject.getInt("userId"));

        return newUser;
    }

    public Post setPost(String post) {
        JSONObject jsonObject = new JSONObject(post);
        Post newPost = new Post();
        if (jsonObject.has("createdDate")){
            Timestamp timeD = new Timestamp((Long) jsonObject.get("createdDate"));
            newPost.setCreatedDate(timeD);
        }
        if (jsonObject.has("updatedDate")){
            Timestamp timeU = new Timestamp((Long) jsonObject.get("updatedDate"));
            newPost.setUpdatedDate(timeU);
        }
        if (jsonObject.has("user")){
            User newUser = setUser(jsonObject.getJSONObject("user").toString());
            newPost.setUser(newUser);
        }
        if (jsonObject.has("postData")){
            newPost.setPostData(jsonObject.getString("postData"));
        }

        return newPost;
    }
}