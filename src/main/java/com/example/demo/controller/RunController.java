package com.example.demo.controller;

import com.example.demo.exeption.user.AuthenticationException;
import com.example.demo.model.RunModel;
import com.example.demo.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RunController {

    private final RunService runService;

    @Autowired
    public RunController(RunService runService) {
        this.runService = runService;
    }

    @GetMapping("/runs")
    public List<RunModel> getAll(HttpSession session) {
        isAuthorize(session);
        return runService.getAll();
    }

    @GetMapping("/runs/{username}")
    public List<RunModel> getAllByUsername(@PathVariable String username, HttpSession session) {
        isAuthorize(session);
        return runService.getAllByUsername(username);
    }

    @GetMapping("/runs/week/{username}")
    public List<RunModel> getWeeksByUsername(@PathVariable String username, HttpSession session) {
        isAuthorize(session);
        return runService.getWeeksByUsername(username);
    }

    @GetMapping("/run/{id}")
    public RunModel getById(@PathVariable long id, HttpSession session) {
        isAuthorize(session);
        return runService.getById(id);
    }

    @PostMapping("/run")
    public String create(@RequestBody RunModel model, HttpSession session) {
        isAuthorize(session);
        return runService.save(model);
    }

    @PutMapping("/run")
    public String update(@RequestBody RunModel model, HttpSession session) {
        isAuthorize(session);
        return runService.save(model);
    }

    @DeleteMapping("/run/{id}")
    public void remove(@PathVariable long id, HttpSession session) {
        isAuthorize(session);
        runService.remove(id);
    }

    private void isAuthorize(HttpSession session) {
        if (session.getAttribute("login") == null) {
            throw new AuthenticationException("User not authorize");
        }
    }
}
