package com.example.gameserver.service;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.MyUserDetails;
import com.example.gameserver.model.domain.Role;
import com.example.gameserver.model.domain.User;
import com.example.gameserver.repository.RoleRepository;
import com.example.gameserver.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public MyUserDetailsServiceImpl(
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with username: %s", username)));

        return new MyUserDetails(user);
    }

    @Transactional
    @Override
    public MyUserDetails login(String username, String password) {
        var user = userRepository.findByUsername(username).orElseThrow();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("");
        }

        return new MyUserDetails(user);
    }

    @Transactional
    @Override
    public void register(String username, String password) throws Exception {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception(String.format("User already exists with username: %s", username));
        }

        var userRole = findRoleByNameOrCreate(Authority.ROLE_USER);

        var user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(userRole);

        userRepository.saveAndFlush(user);
    }

    private Role findRoleByNameOrCreate(String roleName) {
        var userRole = roleRepository.findByName(roleName);
        if (userRole.isPresent()) {
            return userRole.get();
        }

        var role = new Role();
        role.setName(roleName);
        roleRepository.save(role);
        return role;
    }
}
