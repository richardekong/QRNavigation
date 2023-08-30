package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.repository.UserRepo;
import com.team1.qrnavigationproject.response.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private void setUserRepo(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) throws CustomException {
        return userRepo.save(verifyUsernameThenEncodePassword(user));
    }

    @Override
    public User save(User user, UserType userType) throws CustomException {
        var verifiedUser = verifyUsernameThenEncodePassword(user);
        verifiedUser.add(userType);
        return userRepo.save(verifiedUser);
    }

    @Override
    public User save(User user, UserType userType, Role userRole) throws CustomException {
        var verifiedUser = verifyUsernameThenEncodePassword(user);
        verifiedUser.add(userType);
        verifiedUser.add(userRole);
        return userRepo.save(verifiedUser);
    }

    @Override
    public User update(int id, User user) {
        Optional<User> unconfirmedUser = userRepo.findUserById(id);
        if (unconfirmedUser.isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
        user.setId(id);
        return userRepo.save(user);
    }

    @Override
    public User update(User user) {
        return userRepo.save(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<List<User>> findAll() {
        return Optional.of(userRepo.findAll());
    }

    @Override
    public void delete(int id) {
        userRepo
                .findUserById(id)
                .ifPresentOrElse(userRepo::delete, () -> {
                    throw new CustomException(
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            HttpStatus.BAD_REQUEST);
                });
    }


    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Wrong email or password"));

    }

    private User verifyUsernameThenEncodePassword(User user) throws CustomException {
        boolean existsByUsername = userRepo.existsByUsername(user.getUsername());
        boolean existsById = userRepo.existsById(user.getId());
        if (existsById || existsByUsername) {
            String message = "Error (%d): User with %s already exists"
                    .formatted(HttpStatus.CONFLICT.value(), user.getUsername());
            throw new CustomException(message, HttpStatus.CONFLICT);
        }
        //encode user password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }
}
