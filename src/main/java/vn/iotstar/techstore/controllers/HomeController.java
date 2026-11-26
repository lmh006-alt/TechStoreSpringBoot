package vn.iotstar.techstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String root() {
        return "web/home";
    }

    @GetMapping("/home")
    public String home() {
        return "web/home";
    }
}
