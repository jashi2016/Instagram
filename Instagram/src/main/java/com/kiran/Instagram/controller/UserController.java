package com.kiran.Instagram.controller;

import com.kiran.Instagram.model.User;
import com.kiran.Instagram.service.UserService;
import com.kiran.Instagram.util.Util;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    UserService service;

    @Autowired
    Util util;
    @PostMapping("save")// ✅
    private ResponseEntity<String> saveUser(@RequestBody String newUser){
        User user = util.setUser(newUser);
        String name = service.saveUser(user);
        return new ResponseEntity<>("User with User Name " + name + " saved!", HttpStatus.CREATED);
    }

    @GetMapping("get") // ✅ // make it using name too
    private ResponseEntity<String> getUser(@Nullable @RequestParam Integer userId , @Nullable @RequestParam String password){
        JSONArray user = service.getUser(userId, password);
        if(user.isEmpty()) return new ResponseEntity<>("Requested User Doesn't exist!", HttpStatus.BAD_REQUEST);
        else
            return new ResponseEntity<>(user.toString(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete")// ✅
    private ResponseEntity<String> deleteUser(@RequestParam Integer userId){
        if(!service.deleteUser(userId))
            return new ResponseEntity<>("Requested User Doesn't exist!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("User Deleted!", HttpStatus.ACCEPTED);
    }

    @PutMapping("update")// ✅
    private ResponseEntity<String> updateUser(@Nonnull @RequestBody String newUser, @Nullable @RequestParam Integer userId){
        User user = util.setUser(newUser);
        if (userId == null) userId = user.getUserId();
        String name = service.updateUser(user, userId);
        if(name == null)
            return new ResponseEntity<>("Requested User Doesn't exist!", HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>("User " + name + " Updated!", HttpStatus.ACCEPTED);
    }

}