package com.example.gameserver;

import com.example.gameserver.dto.RegisterDto;
import com.example.gameserver.dto.RoleDto;
import com.example.gameserver.service.AuthService;
import com.example.gameserver.service.RoleService;
import com.example.gameserver.util.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInit implements ApplicationRunner {

    private final RoleService roleService;
    private final AuthService authService;

    @Override
    public void run(ApplicationArguments args) {
        saveRole(Authority.USER_ROLE);
        saveRole(Authority.ADMIN_ROLE);

        {
            var registerDto = new RegisterDto("user", "user", "User", "User");
            saveUser(registerDto, false);
        }
        {
            var registerDto = new RegisterDto("admin", "admin", "Admin", "Admin");
            saveUser(registerDto, true);
        }
    }

    private void saveUser(RegisterDto registerDto, boolean admin) {
        try {
            if (admin) {
                authService.registerAdmin(registerDto);
            } else {
                authService.register(registerDto);
            }
        } catch (Exception ignored) {
        }
    }

    private void saveRole(String roleName) {
        try {
            var roleDto = new RoleDto(null, roleName);
            roleService.insert(roleDto);
        } catch (Exception ignored) {
        }
    }
}
