package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User save(User user);

    User save(User user, UserType userType);

    User save(User user, UserType userType, Role userRole);

    User update(int id, User user);

    User update(User user);

    Optional<User> findById(int id);

    Optional<List<User>> findAll();

    void delete(int id);

}
