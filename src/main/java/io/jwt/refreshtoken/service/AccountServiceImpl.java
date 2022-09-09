package io.jwt.refreshtoken.service;

import io.jwt.refreshtoken.entity.AppRole;
import io.jwt.refreshtoken.entity.AppUser;
import io.jwt.refreshtoken.repository.AppRoleRepository;
import io.jwt.refreshtoken.repository.AppUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional  // NB : Transactionnal permet de laisser transiter les informations de l'entité AppRole à AppUser
public class AccountServiceImpl implements AccountService {

    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    // On inject AppUserRepository et AppRoleRepository via un constructeur parce que @Autowired est devenu obselète
    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadOneUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> loadAllUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public List<AppRole> loadAllRoles() {
        return appRoleRepository.findAll();
    }
}
