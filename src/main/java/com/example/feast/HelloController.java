package com.example.feast;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("eventName", "Fifa2018");
        return "index";
    }
}
