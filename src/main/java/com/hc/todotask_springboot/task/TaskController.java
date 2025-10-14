package com.hc.todotask_springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/task")
public class TaskController {

    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> findAll(){
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public Task findById(@PathVariable Long id){
        return taskService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody Task task){
        return taskService.newTask(task);
    }

    @PatchMapping
    public ResponseEntity<Object> update(@RequestBody Task task){
        return taskService.update(task);
    }

    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Task task){
        return taskService.deleteByName(task);
    }
}
