package com.project.es.data.pasiens.controller;

import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.dto.PasienDto;
import com.project.es.data.libary.dto.PasienPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.entity.Pasiens;
import com.project.es.data.libary.service.PasienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private PasienService pasienService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("title","RS || Register");
        model.addAttribute("pasienDto",new PasienDto());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerAdmin(@Valid @ModelAttribute("pasienDto") PasienDto pasienDto,
                                BindingResult result,
                                Model model, RedirectAttributes redirectAttributes){
        try {
            if (result.hasErrors()) {
                model.addAttribute("pasienDto", pasienDto);
                return "register";
            }
            String username = pasienDto.getEmail();
            Pasiens pasiens = pasienService.findByEmail(username);
            if (pasiens != null) {
                model.addAttribute("pasienDto", pasienDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }
            pasienDto.setPassword(passwordEncoder.encode(pasienDto.getPassword()));
            redirectAttributes.addFlashAttribute("success","Register Successfully Login Now");
            pasienService.savePasiens(pasienDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
            return "register";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("title","RS || Login");
        return "login";
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model){
        model.addAttribute("pasien",new PasienPasswordChangeDto());
        model.addAttribute("title","RS || Reset-Password");
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String saveNewPassword(@Valid @ModelAttribute("pasien") PasienPasswordChangeDto pasienPasswordChangeDto,
                                  BindingResult bindingResult,Model model,RedirectAttributes redirectAttributes){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("pasien",pasienPasswordChangeDto);
                model.addAttribute("title","RS || Reset-Password");
                return "reset-password";
            }
            Pasiens pasien = pasienService.findByEmail(pasienPasswordChangeDto.getEmail());
            if(pasien == null){
                model.addAttribute("pasien",pasienPasswordChangeDto);
                model.addAttribute("errors","Cannot Find Email");
                return "reset-password";
            }
            boolean isSuccess = pasienService.resetPasswordPasien(pasienPasswordChangeDto);
            if(isSuccess == false){
                model.addAttribute("pasien",pasienPasswordChangeDto);
                model.addAttribute("errors","Wrong Old Password,Remember Your Last Password");
                return "reset-password";
            }else {
                redirectAttributes.addFlashAttribute("success","Reset Password Success,You Can Login Now");
            }
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("errors","Server Error Try Again Later");
            model.addAttribute("pasien",pasienPasswordChangeDto);
            model.addAttribute("title","RS || Reset-Password");
            return "reset-password";
        }
        return "redirect:/login";
    }

}
