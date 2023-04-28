package com.mypackage;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mypackage.models.ERole;
import com.mypackage.models.Role;
import com.mypackage.models.User;
import com.mypackage.repository.RoleRepository;
import com.mypackage.repository.UserRepository;


@SpringBootApplication
public class AuthentificationandautorizationApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthentificationandautorizationApplication.class, args);
	}
	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	UserRepository userrepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Bean
	public CommandLineRunner demo() {
		return (args) -> {


			// Create users with BCrypt encoded password (user/user, admin/admin)
			Role roleUser = new Role(ERole.ROLE_USER);
			Role roleModerator = new Role(ERole.ROLE_MODERATOR);
			Role roleAdmin = new Role(ERole.ROLE_ADMIN);
			roleRepo.save(roleUser);
			roleRepo.save(roleModerator);
			roleRepo.save(roleAdmin);

			// Affichage
			roleRepo.findAll().forEach(r -> {
				System.out.println(r.toString());

			});

			// ajout user
			// cryptage de mot de passe
			String passworddhaker = "hitunisia", passwordadmin = "administrator",
					passwordlafayette = "lafayette", 
					cryptedPassworddhaker = "",cryptedPasswordadmin = "", 
					cryptedPasswordlafayette = "";
			cryptedPasswordadmin = encoder.encode(passwordadmin);
			cryptedPassworddhaker = encoder.encode(passworddhaker);
			cryptedPasswordlafayette = encoder.encode(passwordlafayette);
			// setting user role
			User admin = new User("admin", "admin@gmail.com", cryptedPasswordadmin);
			User bmdhaker = new User("bmdhaker", "bmdhaker@gmail.com", cryptedPassworddhaker);
			User lafayette = new User("lafayette", "lafayette@gmail.com", cryptedPasswordlafayette);

			Set<Role> rolesadmin = new HashSet<>();
			Set<Role> rolesmoderator = new HashSet<>();
			Set<Role> rolesuser = new HashSet<>();
			rolesadmin.add(roleAdmin);
			rolesmoderator.add(roleModerator);
			rolesuser.add(roleUser);

			admin.setRoles(rolesadmin);
			System.out.println("roles admin");
			admin.getRoles().forEach(r -> {
				System.out.println(r.toString());
			});

			lafayette.setRoles(rolesmoderator);
			bmdhaker.setRoles(rolesuser);
			userrepository.save(admin);
			userrepository.save(bmdhaker);
			userrepository.save(lafayette);
		};

	}
}
