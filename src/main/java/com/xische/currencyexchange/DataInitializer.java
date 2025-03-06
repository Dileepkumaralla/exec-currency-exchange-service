package com.xische.currencyexchange;

import com.xische.currencyexchange.model.User;
import com.xische.currencyexchange.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DataInitializer.class);
    private final UserRepository users;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(value = ApplicationReadyEvent.class)
    public void init() {
        log.info("start data initialization...");
        this.users.deleteAll()
                .thenMany(
                        Flux.just("user", "admin")
                                .flatMap(username -> {
                                    List<String> roles = "user".equals(username) ?
                                            List.of("ROLE_USER") : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                                    User user = new User();
                                    user.setUsername(username);
                                    user.setPassword(passwordEncoder.encode("password"));
                                    user.setEmail(username + "@domain.com");
                                    user.setRoles(roles);

                                    return this.users.save(user);
                                })
                )
                .subscribe(
                        user -> log.info("User inserted: {}", user),
                        error -> log.error("Error inserting user: {}", error.getMessage()),
                        () -> log.info("Data initialization complete.")
                );
    }
}