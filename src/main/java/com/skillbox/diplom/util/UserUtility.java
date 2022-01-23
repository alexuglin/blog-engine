package com.skillbox.diplom.util;

import com.skillbox.diplom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtility {

    private final UserRepository userRepository;

    public static String getCurrentUserEmail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        User user = (User) authentication.getPrincipal();
        return user.getUsername();
    }

    public com.skillbox.diplom.model.User getCurrentUser() {
        String email = getCurrentUserEmail();
        return userRepository.findByEmail(email).orElse(null);
    }
}
