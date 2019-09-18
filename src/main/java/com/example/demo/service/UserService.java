package com.example.demo.service;

import com.example.demo.exeption.user.AuthenticationException;
import com.example.demo.exeption.user.UserAlreadyExistException;
import com.example.demo.exeption.user.UserNotFoundException;
import com.example.demo.model.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

   private volatile static Map<String, String> users = new ConcurrentHashMap<>();

   @PostConstruct
   public void init() {
       users.put("admin", "admin");
   }

    public String save(UserModel model) {
        String user = users.get(model.getName());
        if (user != null) {
            throw new UserAlreadyExistException(String.format("User with name %s already exist", model.getName()));
        }
        users.put(model.getName(), model.getPassword());
        return String.format("User with name %s was created!", model.getName());
    }


    public boolean login(UserModel model) {
        String user = users.get(model.getName());
        if (user == null) {
            throw new UserNotFoundException(String.format("User with name %s not found", model.getName()));
        }
        if (user.equals(model.getPassword())) {
            return true;
        } else {
            throw new AuthenticationException("Wrong password!");
        }
    }
}
