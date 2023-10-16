package com.example.gameserver;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.domain.Role;
import com.example.gameserver.model.domain.User;
import com.example.gameserver.repository.RoleRepository;
import com.example.gameserver.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DatabaseInit(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        saveRole(Authority.ROLE_USER);
        saveRole(Authority.ROLE_ADMIN);

        {
            var user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.addRole(roleRepository.findByName(Authority.ROLE_USER).get());
            saveUser(user);
        }
        {
            var user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.addRole(roleRepository.findByName(Authority.ROLE_USER).get());
            user.addRole(roleRepository.findByName(Authority.ROLE_ADMIN).get());
            saveUser(user);
        }
    }

    private void saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return;
        }

        userRepository.save(user);
    }

    private void saveRole(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            return;
        }

        var role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
    }
}
