package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findUserById(int id);

    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

}

