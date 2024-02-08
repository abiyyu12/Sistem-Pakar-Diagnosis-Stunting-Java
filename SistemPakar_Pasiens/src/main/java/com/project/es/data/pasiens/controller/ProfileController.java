package com.project.es.data.pasiens.controller;

import com.project.es.data.libary.dto.GejalaDto;
import com.project.es.data.libary.dto.PasienDto;
import com.project.es.data.libary.service.PasienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private PasienService pasienService;

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model){
        if (principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","RS || User Profile");
        model.addAttribute("email",principal.getName());
        model.addAttribute("pasien",pasienService.findPasienByEmailDto(principal.getName()));
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("pasien") PasienDto pasienDto,
                                BindingResult result,
                                Model model, RedirectAttributes redirectAttributes){
        try {
            if (result.hasErrors()) {
                model.addAttribute("pasien", pasienDto);
                model.addAttribute("email",pasienDto.getEmail());
                System.out.println("masih ada yang belum diisi");
                model.addAttribute("title","RS || User Profile");
                return "profile";
            }
            // Update Pasien Profile
            pasienService.updatePasiens(pasienDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("pasien", pasienDto);
            model.addAttribute("email",pasienDto.getEmail());
            model.addAttribute("title","RS || User Profile");
            return "profile";
        }
        redirectAttributes.addFlashAttribute("success","Profile Has Been Updated");
        return "redirect:/profile";
    }
}
