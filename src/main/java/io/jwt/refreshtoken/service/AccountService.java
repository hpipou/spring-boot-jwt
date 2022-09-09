package io.jwt.refreshtoken.service;

import io.jwt.refreshtoken.entity.AppRole;
import io.jwt.refreshtoken.entity.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username,String roleName);
    AppUser loadOneUser(String username);
    List<AppUser> loadAllUsers();
    List<AppRole> loadAllRoles();
}
