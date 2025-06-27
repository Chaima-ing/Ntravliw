package travalagency.com.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/TravalAgency")
    public String travalAgency() {
        return "index.html";
    }
}
