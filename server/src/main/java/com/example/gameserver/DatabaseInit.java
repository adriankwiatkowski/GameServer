package com.example.gameserver;

import com.example.gameserver.model.Authority;
import com.example.gameserver.model.dto.RegisterDto;
import com.example.gameserver.model.dto.RoleDto;
import com.example.gameserver.service.MyUserDetailsService;
import com.example.gameserver.service.RoleService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements ApplicationRunner {

    private final RoleService roleService;
    private final MyUserDetailsService myUserDetailsService;

    public DatabaseInit(RoleService roleService, MyUserDetailsService myUserDetailsService) {
        this.roleService = roleService;
        this.myUserDetailsService = myUserDetailsService;
    }

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
                myUserDetailsService.registerAdmin(registerDto);
            } else {
                myUserDetailsService.register(registerDto);
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
