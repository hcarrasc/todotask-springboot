package com.hc.todotask_springboot.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    HashMap<String, Object> responseData;

    private final  TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public Task findById(Long id) {
        return this.taskRepository.findById(id).get();
    }

    public ResponseEntity<Object> newTask(Task task) {
        Optional<Task> taskFromRepo = taskRepository.findTaskByTitle(task.getTitle());
        responseData = new HashMap<>();

        if (taskFromRepo.isPresent()){
            responseData.put("error", "task already exists");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        } else {
            taskRepository.save(task);
            responseData.put("task", task);
            responseData.put("status", "task added successfully");
            return new ResponseEntity<>(responseData, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<Object> update(Task task) {
        Optional<Task> taskFromRepo = taskRepository.findTaskByTitle(task.getTitle());
        responseData = new HashMap<>();

        if (taskFromRepo.isPresent()){
            task.setId(taskFromRepo.get().getId());
            taskRepository.save(task);
            responseData.put("task", task);
            responseData.put("status", "task updated successfully");
            return new ResponseEntity<>(responseData, HttpStatus.ACCEPTED);

        } else {
            responseData.put("error", "task not found");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
    }

    public ResponseEntity<Object> deleteByName(Task task) {
        Optional<Task> taskFromRepo = taskRepository.findTaskByTitle(task.getTitle());
        responseData = new HashMap<>();
        if (taskFromRepo.isPresent()){
            taskRepository.deleteById(taskFromRepo.get().getId());
            responseData.put("task", task);
            responseData.put("status", "task deleted successfully");
            return new ResponseEntity<>(responseData, HttpStatus.ACCEPTED);

        } else {
            responseData.put("error", "task not found");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
    }
}
