package com.it211_ss18_04.config;

import com.it211_ss18_04.entity.User;
import com.it211_ss18_04.entity.Role;
import com.it211_ss18_04.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(
            UserRepository repo,
            PasswordEncoder encoder) {

        return args -> {

            repo.save(User.builder()
                    .username("admin")
                    .password(encoder.encode("123"))
                    .role(Role.ADMIN)
                    .build());

            repo.save(User.builder()
                    .username("staff")
                    .password(encoder.encode("123"))
                    .role(Role.STAFF)
                    .build());

            repo.save(User.builder()
                    .username("customer")
                    .password(encoder.encode("123"))
                    .role(Role.CUSTOMER)
                    .build());
        };
    }
}