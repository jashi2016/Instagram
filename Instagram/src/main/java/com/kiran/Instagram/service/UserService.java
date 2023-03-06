package com.kiran.Instagram.service;

import com.kiran.Instagram.model.User;
import com.kiran.Instagram.repository.UserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository repository;
    public String saveUser(User user) {
        User statusChange = this.findByEmail(user.getEmail());
        if(statusChange != null){
            statusChange.setStatus(true);
            this.clone(statusChange, user);
            repository.save(statusChange);
        }
        else repository.save(user);
        return user.getFirstName() + " " + user.getLastName();
    }

    private User findByEmail(String email) {
        List<User> userList = repository.findAll();
        for (User user: userList) if(user.getEmail().equals(email)) return user;
        return null;
    }

    public JSONArray getUser(Integer userId, String password) {
        String pass = "pass";
        JSONArray list = new JSONArray();
        if(userId == null){
            List<User> userList = repository.findAll();
            for(User user: userList){
                if(user.isStatus() || pass.equals(password))
                    list.put(userToJsonObject(user));
            }
        }else if (repository.findById(userId).isPresent()) {
            User user = repository.findById(userId).get();
            if(user.isStatus())
                list.put(userToJsonObject(user));
        }
        return list;
    }

    public static JSONObject userToJsonObject(User user){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("userId", user.getUserId());
        jsonObject.put("firstName", user.getFirstName());
        jsonObject.put("lastName", user.getLastName());
        jsonObject.put("age", user.getAge());
        jsonObject.put("phoneNumber", user.getPhoneNumber());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("status", user.isStatus());
        return jsonObject;
    }

    public boolean deleteUser(Integer userId) {
        if(repository.findById(userId).isPresent()){
            User user = repository.findById(userId).get();
            if(user.isStatus()){
                user.setStatus(false);
                repository.save(user);
                return true;
            }else return false;
        }else return false;
    }

    public String updateUser(User newUser, Integer userId) {
        Integer id = userId;

        if(id == null) id = newUser.getUserId();

        if(repository.findById(id).isPresent()){
            User user = repository.findById(id).get();
            this.clone(user, newUser);
            repository.save(user);
            return user.getFirstName() + " " + user.getLastName();
        }else return null;
    }

    private void clone(User a, User b){ //b -> a
        if(b.getUserId() != null)
            a.setUserId(b.getUserId());
        if(b.getFirstName() != null)
            a.setFirstName(b.getFirstName());
        if(b.getLastName() != null)
            a.setLastName(b.getLastName());
        if(b.getAge() != null)
            a.setAge(b.getAge());
        if(b.getPhoneNumber() != null)
            a.setPhoneNumber(b.getPhoneNumber());
        if(b.getEmail() != null)
            a.setEmail(b.getEmail());
        a.setStatus(b.isStatus());
    }

//    public User saveUser1(User user) {
//        return repository.save(user);
//    }
}