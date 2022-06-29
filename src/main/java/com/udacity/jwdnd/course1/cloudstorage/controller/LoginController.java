package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping()
public class LoginController {

    @GetMapping("/login")
    public String loginView(Model model) {
        return "login";
    }

    @PostMapping("/logout")
    public RedirectView logoutView(HttpServletRequest request, Model model) throws ServletException {
        System.out.println("Logout breakpoint hit");
        model.addAttribute("logoutSuccess", true);
        request.logout();
        return new RedirectView("/login");

    }
}
