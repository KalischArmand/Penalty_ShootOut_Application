package com.example.joc_online.controller;

import com.example.joc_online.model.User;
import com.example.joc_online.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public void login(@RequestParam String username,
                      @RequestParam String password,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        ResponseEntity<?> result = userService.login(user);

        if (result.getStatusCode().is2xxSuccessful()) {
            request.getSession().setAttribute("username", username);
            System.out.println("Username saved in session: " + username); // Debugging log
            response.sendRedirect("/game.html");
        } else {
            response.sendRedirect("/login.html?error=" + result.getBody());
        }
    }
    @PostMapping("/register")
    public void register(@RequestParam String username,
                         @RequestParam String password,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        ResponseEntity<?> result = userService.register(user);

        if (result.getStatusCode().is2xxSuccessful()) {
            // Salvează username-ul în sesiune
            request.getSession().setAttribute("username", username);
            response.sendRedirect("/game.html");
        } else {
            response.sendRedirect("/register.html?error=" + result.getBody());
        }
    }



    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/login.html");
    }

    @GetMapping("/get-username")
    @ResponseBody
    public String getUsername(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        return username != null ? username : "";
    }
}
