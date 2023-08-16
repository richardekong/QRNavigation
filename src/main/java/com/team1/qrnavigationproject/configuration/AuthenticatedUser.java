package com.team1.qrnavigationproject.configuration;

import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

public interface AuthenticatedUser {

    static User requestCurrentUser(Authentication auth, UserService userService) {
        User authenticatedUser = null;
        if (auth != null && auth.isAuthenticated() && auth.getName() != null){
            authenticatedUser = userService
                            .findUserByUsername(auth.getName())
                            .orElseThrow(() -> new CustomException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED));
        }
        return authenticatedUser;
    }
}
