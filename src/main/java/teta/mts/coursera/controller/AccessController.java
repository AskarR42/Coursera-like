package teta.mts.coursera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access_denied")
public class AccessController {

    @GetMapping
    public String accessDenied() {
        return "access_denied";
    }

}
