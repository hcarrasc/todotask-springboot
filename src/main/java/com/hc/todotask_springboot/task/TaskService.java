package com.hc.todotask_springboot.task;

import com.hc.todotask_springboot.user.User;
import com.hc.todotask_springboot.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {

    HashMap<String, Object> responseData;

    private final  TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Task> findAll() {
        return this.taskRepository.findAll();
    }

    public Task findById(Long id) {
        return this.taskRepository.findById(id).get();
    }

    public List<Task> findByUsername(String username) {
        return this.taskRepository.findByUserUsername(username);
    }

    @Transactional
    public ResponseEntity<Object> createTaskForUser(Task task, String username) {

        Map<String, Object> responseData = new HashMap<>();

        User user = userRepository.findUserByUsername(username)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setUsername(username);
                    return userRepository.save(newUser);
                });

        Optional<Task> taskFromRepo = taskRepository
                .findByTitleAndUserUsername(task.getTitle(), username);

        if (taskFromRepo.isPresent()) {
            responseData.put("error", "task already exists for this user");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        task.setUser(user);
        task.setCreateTime(Instant.now());
        Task savedTask = taskRepository.save(task);

        user.getTasks().add(savedTask);

        responseData.put("task", savedTask);
        responseData.put("status", "task added successfully");
        return new ResponseEntity<>(responseData, HttpStatus.CREATED);

    }

    @Transactional
    public ResponseEntity<Object> update(Task task, String username) {

        responseData = new HashMap<>();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getId() == null) {
            responseData.put("error", "user not found");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        Optional<Task> taskFromRepo = taskRepository
                .findByTitleAndUserUsername(task.getTitle(), username);
        if(taskFromRepo.isEmpty()){
            responseData.put("error", "task not found");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
        task.setUser(user);
        task.setCreateTime(Instant.now());
        task.setId(taskFromRepo.get().getId());
        taskRepository.save(task);
        responseData.put("task", task);
        responseData.put("status", "task updated successfully");
        return new ResponseEntity<>(responseData, HttpStatus.ACCEPTED);

    }

    @Transactional
    public ResponseEntity<Object> deleteByName(Task task, String username) {

        Optional<Task> taskFromRepo = taskRepository
                .findByTitleAndUserUsername(task.getTitle(), username);

        responseData = new HashMap<>();
        if (taskFromRepo.isPresent()){
            taskRepository.deleteByTitleAndUserUsername(task.getTitle(), username);
            responseData.put("status", "task deleted successfully");
            return new ResponseEntity<>(responseData, HttpStatus.ACCEPTED);

        } else {
            responseData.put("error", "task not found");
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }
    }
}
