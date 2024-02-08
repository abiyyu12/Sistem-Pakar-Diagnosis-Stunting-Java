package com.project.es.data.admin.controller;

import com.project.es.data.libary.dto.AdminDto;
import com.project.es.data.libary.dto.AdminPasswordChangeDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/profile")
    public String getProfile(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        AdminDto admin = adminService.findByEmailToDto(principal.getName());
        model.addAttribute("profileAdmin",admin);
        model.addAttribute("title","Admin || Profile");
        return "profile";
    }

    @PostMapping(value = "/profile")
    public String updateProfile(@Valid @ModelAttribute("profileAdmin")AdminDto adminDto, BindingResult bindingResult,
                                Model model, RedirectAttributes redirectAttributes,Principal principal){
        try {
            if(bindingResult.hasErrors()){
                model.addAttribute("profileAdmin",adminDto);
                model.addAttribute("title","Admin || Profile");
                return "profile";
            }
            Admin admin = adminService.findByEmail(principal.getName());
            if(admin.getEmail().equals(adminDto.getEmail())){
                adminService.updateAdminProfile(adminDto);
                redirectAttributes.addFlashAttribute("success","Profile Has Been Changed");
            }
        }catch (Exception e){
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errors","Servers Error Try Agai Later");
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping(value = "/changePassword")
    public String changePasswordAdmin(Principal principal,@RequestParam("oldPassword") String oldPassword,
                                      @RequestParam("newPassword") String newPassword,RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        if(oldPassword == null || newPassword == null){
            redirectAttributes.addFlashAttribute("errors","Please Insert Old Password and New Password");
        }
        AdminPasswordChangeDto adminPasswordChangeDto = new AdminPasswordChangeDto();
        adminPasswordChangeDto.setEmail(principal.getName());
        adminPasswordChangeDto.setOldPassword(oldPassword);
        adminPasswordChangeDto.setNewPassword(newPassword);
        boolean isSuccess = adminService.passwordChanger(adminPasswordChangeDto);
        if(isSuccess != true){
            redirectAttributes.addFlashAttribute("errors","Old Password and New Password Not Same");
        }else{
            redirectAttributes.addFlashAttribute("success","New Password Has Changed");
        }
        return "redirect:/profile";
    }

}