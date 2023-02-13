
package com.greenart.yogio.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String getMain() {
        return "/index";
    }
    @GetMapping("/main")
    public String getMain2() {
        return "/main";
    }
    @GetMapping("/omain")
    public String getMain3() {
        return "/omain";
    }
}
