package com.example.demo.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestBody UserModel model, HttpSession session) {
        if (session.getAttribute("login") == null) {
            return userService.save(model);
        }
        return "Already login in";
    }

    @PostMapping("/login")
    public void login(@RequestBody UserModel model, HttpSession session) {
        if (userService.login(model)) {
            session.setAttribute("login", true);
        }
    }
}
