package com.hc.todotask_springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/task")
public class TaskController {

    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/user/{username}")
    public List<Task> findAll(@PathVariable String username){
        return taskService.findByUsername(username);
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id){
        return taskService.findById(id);
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<Object> save(@RequestBody Task task, @PathVariable String username) {
        return taskService.createTaskForUser(task, username);
    }

    @PatchMapping("/user/{username}")
    public ResponseEntity<Object> update(@RequestBody Task task, @PathVariable String username){
        return taskService.update(task, username);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Object> delete(@RequestBody Task task, @PathVariable String username){
        return taskService.deleteByName(task, username);
    }
}
