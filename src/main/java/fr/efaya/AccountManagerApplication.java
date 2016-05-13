package fr.efaya;

import fr.efaya.database.UsersRepository;
import fr.efaya.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class AccountManagerApplication implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;

	public static void main(String[] args) {
		SpringApplication.run(AccountManagerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        List<User> users = usersRepository.findAll();
        if (users.isEmpty()) {
            User defaultUser = new User("admin", "2319efaya");
            usersRepository.save(defaultUser);
        }
    }
}
