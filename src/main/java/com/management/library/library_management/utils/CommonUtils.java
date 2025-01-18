package com.management.library.library_management.utils;

import com.management.library.library_management.entity.User;
import com.management.library.library_management.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j

@Component
public class CommonUtils {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;

    public User getApplicationUser(HttpServletRequest request) {
        String userId = null;
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUsername(jwt);
            User user = userService.findUserByUserName(userId);
            return user;

        } else {
            log.info("Error while getUserFromResponseToken ");
            return null;
        }

    }
}
