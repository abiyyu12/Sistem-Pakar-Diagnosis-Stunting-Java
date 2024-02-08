package com.project.es.data.pasiens.controller;

import com.project.es.data.libary.service.PenyakitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PenyakitController {

    @Autowired
    private PenyakitService penyakitService;

    @GetMapping("/penyakit")
    public String getPenyakit(Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","RS || Penyakit Stunting");
        model.addAttribute("penyakit",penyakitService.getPenyakit());
        return "penyakit-page";
    }
}
