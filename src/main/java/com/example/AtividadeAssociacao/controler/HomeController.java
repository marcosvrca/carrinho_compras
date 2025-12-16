package com.example.AtividadeAssociacao.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirecionarParaProdutos() {
        return "redirect:/produtos";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
