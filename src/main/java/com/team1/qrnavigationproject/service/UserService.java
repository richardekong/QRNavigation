package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User save(User user) throws Exception;

    User save(User user, UserType userType) throws Exception;

    User save(User user, UserType userType, Role userRole) throws Exception;

    User update(int id, User user) throws Exception;

    User update(User user) throws Exception;

    Optional<User> findById(int id);

    Optional<User> findUserByUsername(String username);

    Optional<List<User>> findAll();

    void delete(int id);

}
