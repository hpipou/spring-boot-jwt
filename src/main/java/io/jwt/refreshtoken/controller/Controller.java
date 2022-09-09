package io.jwt.refreshtoken.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void addRoleToUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        accountService.addRoleToUser(request.getParameter("username"), request.getParameter("roleName"));
        new ObjectMapper().writeValue(
                response.getOutputStream(),
                "Role Ajouté avec succès"
        );
    }

    @PostMapping("/userInfo")
    public void loadOneUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("application/json");
        new ObjectMapper().writeValue(
                response.getOutputStream(),
                accountService.loadOneUser(request.getParameter("username"))
        );
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
