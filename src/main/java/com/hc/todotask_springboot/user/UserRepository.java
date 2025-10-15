package com.hc.todotask_springboot.user;

import com.hc.todotask_springboot.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String name);

}
