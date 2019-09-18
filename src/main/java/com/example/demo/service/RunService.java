package com.example.demo.service;

import com.example.demo.exeption.run.RunNotFoundException;
import com.example.demo.model.RunModel;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class RunService {

    private volatile static List<RunModel> models = new CopyOnWriteArrayList<>();
    private static AtomicLong count = new AtomicLong(1);


    public List<RunModel> getAll() {
        return models;
    }

    public List<RunModel> getAllByUsername(String username) {
        return models.stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
    }

    public List<RunModel> getWeeksByUsername(String username) {
        return Collections.emptyList();
    }

    public RunModel getById(long id) {
        List<RunModel> collect = models.stream().filter(x -> x.getId() == id).limit(1).collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new RunNotFoundException(String.format("Run with id %s not found", id));
        }
        return collect.get(0);
    }

    public String save(RunModel model) {
        try {
            RunModel byId = getById(model.getId());
            byId.setTime(model.getTime());
            byId.setDistance(model.getDistance());
            byId.setUsername(model.getUsername());
            byId.setDate(model.getDate());
            return String.format("Run with id %s for user %s updated", model.getId(), model.getUsername());
        } catch (RunNotFoundException ex) {
            model.setId(generateId());
            models.add(model);
            return String.format("Run with id %s for user %s created", model.getId(), model.getUsername());
        }
    }

    public void remove(long id) {
        RunModel byId = getById(id);
        models.remove(byId);
    }

    private long generateId() {
       return count.getAndIncrement();
    }
}
