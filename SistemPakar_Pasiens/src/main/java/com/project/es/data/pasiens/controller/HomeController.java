package com.project.es.data.pasiens.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHome(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession, Principal principal){
        String login = httpServletRequest.getParameter("login");
        model.addAttribute("login",login);
        if(login != null){
            httpSession.setAttribute("username",principal.getName());
            System.out.println(principal.getName());
        }
        model.addAttribute("title","RS || INDEX");
        return "index";
    }
}
