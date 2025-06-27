package travalagency.com.demo;

import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public Account register(@RequestBody Account account) {
        return authService.register(account);
    }

    @GetMapping("/getAccountById")
    public Account getById(@RequestParam String id) {
        System.out.println("-----------------------------------------------------------------received id");
        return authService.getById(id); 
    }

    @GetMapping("/login")
    public String login(
            @RequestParam String id,
            @RequestParam String password
    ) {
        boolean isAuthenticated = authService.login(id, password);
        if (isAuthenticated) {
            return "success";
        } else {
            return "failure";
        }
    }
}
