package com.coffee_management_system.main_api.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Replace with actual user lookup (e.g., from database)
        if ("testuser".equals(username)) {
            return User.withUsername("testuser")
                    .password("$2a$10$jkEZ81VOW0aFAUYeo6LPPukp/gbOMeuHyll0Q.crkyBAeJE75oDnK") // Use BCrypt for password NOT HARD-CODED
                    .roles("USER")
                    .build();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }
}
