package com.hc.todotask_springboot.task;

import com.hc.todotask_springboot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findTaskByTitle(String name);
    List<Task> findByUserUsername(String username);
    Optional<Task> findByTitleAndUserUsername(String title, String username);
    void deleteByTitleAndUserUsername(String title, String username);
}
