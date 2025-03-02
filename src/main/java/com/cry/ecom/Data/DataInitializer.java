package com.cry.ecom.Data;

import com.cry.ecom.Entity.Role;
import com.cry.ecom.Entity.User;
import com.cry.ecom.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_USER");
        createDefaultRoleIfNotExits(defaultRoles);
        createDefaultUserIfNotExits();
        createDefaultAdminIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
        if (userRoleOptional.isEmpty()) {
            System.out.println("ROLE_USER not found. Skipping user creation.");
            return;
        }
        Role userRole = userRoleOptional.get();
        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "sam" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("The User");
            user.setLastName("User" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(userRole));
            userRepository.save(user);
            System.out.println("Default vet user " + i + " created successfully.");
        }
    }

    private void createDefaultAdminIfNotExits() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@email.com";
            if (userRepository.existsByEmail(defaultEmail)) {
                continue;
            }
            User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Admin" + i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRoles(Set.of(adminRole));
            userRepository.save(user);
            System.out.println("Default admin user " + i + " created successfully.");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> roles) {
        roles.forEach(roleName -> {
            if (!roleRepository.findByName(roleName).isPresent()) {
                Role role = new Role(roleName);
                roleRepository.save(role);
                System.out.println("Default role " + roleName + " created successfully.");
            }
        });
    }

}
