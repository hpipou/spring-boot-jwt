package io.jwt.refreshtoken.controller;

import io.jwt.refreshtoken.entity.AppRole;
import io.jwt.refreshtoken.entity.AppUser;
import io.jwt.refreshtoken.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class Controller {

    private AccountService accountService;

    public Controller(AccountService accountServicex) {
        this.accountService = accountServicex;
    }

    @PostMapping("/user")
    public AppUser addNewUser(@RequestBody AppUser appUser){
        return accountService.addNewUser(appUser);
    }

    @PostMapping("/role")
    public AppRole addNewRole(@RequestBody AppRole appRole){
        return accountService.addNewRole(appRole);
    }

    @PostMapping("/roleToUser")
    public String addRoleToUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        accountService.addRoleToUser(request.getParameter("username"), request.getParameter("roleName"));
        return "Role ajouté avec succès";
    }

    @PostMapping("/userInfo")
    public AppUser loadOneUser(@RequestBody String username){
        return accountService.loadOneUser(username);
    }

    @GetMapping("/users")
    public List<AppUser> loadAllUsers(){
        return accountService.loadAllUsers();
    }

    @GetMapping("/roles")
    public List<AppRole> loadAllRoles(){
        return accountService.loadAllRoles();
    }

}
