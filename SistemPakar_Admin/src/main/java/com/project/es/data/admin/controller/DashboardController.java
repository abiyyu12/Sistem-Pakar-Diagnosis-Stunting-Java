package com.project.es.data.admin.controller;

import com.project.es.data.libary.dto.DiagnosisChart;
import com.project.es.data.libary.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private GejalaService gejalaService;

    @Autowired
    private PasienService pasienService;

    @Autowired
    private PenyakitService penyakitService;


    @GetMapping(value = "/home")
    public String dashboardAdmin(Model model, HttpServletRequest httpServletRequest, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","Admin | Login");
        String login = httpServletRequest.getParameter("login");
        List<String> listPenyakit = new ArrayList<>();
        List<Long> value = new ArrayList<>();
        List<DiagnosisChart> chartDiagnosis = diagnosisService.getChartDiagnosis();
        for (DiagnosisChart diagnosis : chartDiagnosis) {
            listPenyakit.add(diagnosis.getNamaPenyakit());
            value.add(diagnosis.getTotalPenyakit());
        }

        model.addAttribute("login",login);
        model.addAttribute("cAdmin",adminService.countAdmin());
        model.addAttribute("listPenyakit",listPenyakit);
        model.addAttribute("value",value);
        model.addAttribute("year", LocalDateTime.now().getYear());
        model.addAttribute("cDiagnosis",diagnosisService.countDiagnosis());
        model.addAttribute("cPasien",pasienService.pasienCount());
        model.addAttribute("cGejala",gejalaService.gejalaCount());
        model.addAttribute("cPenyakit",penyakitService.penyakitCount());
        return "index";
    }
}