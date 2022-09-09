package io.jwt.refreshtoken.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception{

        String authToken=request.getHeader("Authorization");
        if(authToken!=null && authToken.startsWith("Bearer")){
            try {

                String refreshToken=authToken.substring(7);
                Algorithm algorithm=Algorithm.HMAC256("SECRET");
                JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String username=decodedJWT.getSubject();

                AppUser appUser=accountService.loadOneUser(username);
                String jwtAccessToken=JWT.create()
                        .withSubject(username)
                        .withIssuer(request.getRequestURL().toString())
                        .withExpiresAt(new Date(System.currentTimeMillis()+5*60*1000))
                        .withClaim("roles",appUser.getAppRoles().stream().map(r->r.getRoleName()).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String,String> token = new HashMap<>();
                token.put("jwtAccessToken", jwtAccessToken);
                token.put("jwtRefreshToken", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),token);

            }catch (Exception e){
                Map<String,String> tokenIntrouvable=new HashMap<>();
                tokenIntrouvable.put("ERREUR","REFRESH TOKEN INVALIDE");
                response.setStatus(403);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(),tokenIntrouvable);
            }

        }else{
            Map<String,String> tokenIntrouvable=new HashMap<>();
            tokenIntrouvable.put("ERREUR","REFRESH TOKEN INTROUVABLE");
            response.setStatus(403);
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(),tokenIntrouvable);
        }

    }

}
