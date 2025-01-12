package com.example.joc_online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping("/")
    public String index() {
        return "index"; // templates/index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // templates/login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // templates/register.html
    }

    @GetMapping("/game")
    public String game() {
        return "game"; // templates/game.html
    }
}