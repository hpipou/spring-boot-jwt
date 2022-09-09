package io.jwt.refreshtoken;

import io.jwt.refreshtoken.entity.AppRole;
import io.jwt.refreshtoken.entity.AppUser;
import io.jwt.refreshtoken.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class RefreshtokenApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefreshtokenApplication.class, args);
	}

	@Bean
	CommandLineRunner start(AccountService accountService){
		return args -> {
			accountService.addNewRole(new AppRole(null,"USER"));
			accountService.addNewRole(new AppRole(null,"ADMIN"));
			accountService.addNewRole(new AppRole(null,"MANAGER"));

			accountService.addNewUser(new AppUser(null,"user","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
			accountService.addNewUser(new AppUser(null,"manager","1234",new ArrayList<>()));

			accountService.addRoleToUser("user", "USER");
			accountService.addRoleToUser("admin", "ADMIN");
			accountService.addRoleToUser("admin", "USER");
			accountService.addRoleToUser("manager", "MANAGER");
			accountService.addRoleToUser("manager", "USER");
		};
	}

}
