package travalagency.com.demo;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authRepo;

    public AuthenticationService(AuthenticationRepository authRepo) {
        this.authRepo = authRepo;
    }

    public Account register(Account account) {
        return authRepo.save(account);
    }

    public Account getById(String id) {
    Optional<Account> accountOptional = authRepo.findById(id);

    if (accountOptional.isPresent()) {
        Account account = accountOptional.get();
        System.out.println("-------------------------------Account found: " );
        return account;
    } else {
        System.out.println("-------------------------------Account NOT found");
        return null; // or throw a custom exception
    }
}


    public Account login(String id, String password) {
        Optional<Account> accountOptional = authRepo.findAccountByIdAndPassword(id, password);
        Account account = accountOptional.get();
        return account;
    }
}
