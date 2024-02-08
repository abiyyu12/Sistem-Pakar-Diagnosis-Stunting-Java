package com.project.es.data.admin.controller;


import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.dto.AdminPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.service.AdminService;
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
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "login")
    public String login(Model model){
        return "login";
    }

    @GetMapping(value = "/register")
    public String getPageRegister(Model model){
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                                BindingResult result,
                                Model model){
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
            String username = adminDto.getEmail();
            Admin admin = adminService.findByEmail(username);
            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("error", "Email has been register!");
                return "register";
            }
            if (adminDto.getPassword().equals(adminDto.getConfirmPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.saveAdmin(adminDto);
                model.addAttribute("success", "Register successfully!");
            } else {
                model.addAttribute("error", "Password is incorrect");
                model.addAttribute("adminDto", adminDto);
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Server is error, try again later!");
            return "register";
        }
        return "login";
    }

    @GetMapping(value = "/reset-password")
    public String resetPasswordPage(Model model){
        model.addAttribute("adminPassword",new AdminPasswordChangeDto());
        return "reset-password";
    }

    @PostMapping(value = "/reset-password")
    public String resetPassword(@Valid @ModelAttribute("adminPassword") AdminPasswordChangeDto adminPasswordChangeDto,BindingResult bindingResult,
                                Model model,RedirectAttributes redirectAttributes){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("adminPassword",adminPasswordChangeDto);
                return "reset-password";
            }
            Admin byEmail = adminService.findByEmail(adminPasswordChangeDto.getEmail());
            if (byEmail == null){
                redirectAttributes.addFlashAttribute("errors","Email Not Found,Please Register Your Account");
                return "reset-password";
            }
            boolean isSuccess = adminService.passwordChanger(adminPasswordChangeDto);
            if (isSuccess == false){
                redirectAttributes.addFlashAttribute("errors","Old Password Wrong,Remember Your Previous Password");
                return "redirect:/reset-password";
            }else {
                redirectAttributes.addFlashAttribute("success","Password Has Change,You Can Login Now");
                return "redirect:/login";
            }
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errors","Server Error Try Again Later");
            return "redirect:/reset-password";
        }
    }

}
