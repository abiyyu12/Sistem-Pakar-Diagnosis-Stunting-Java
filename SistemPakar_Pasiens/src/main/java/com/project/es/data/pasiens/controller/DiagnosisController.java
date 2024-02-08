package com.project.es.data.pasiens.controller;

import com.project.es.data.libary.dto.DetailDiagnosisDto;
import com.project.es.data.libary.dto.PenyakitDto;
import com.project.es.data.libary.entity.DetailDiagnosis;
import com.project.es.data.libary.entity.Diagnosis;
import com.project.es.data.libary.entity.Pasiens;
import com.project.es.data.libary.entity.Penyakit;
import com.project.es.data.libary.service.DiagnosisService;
import com.project.es.data.libary.service.GejalaService;
import com.project.es.data.libary.service.PasienService;
import com.project.es.data.libary.service.PenyakitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DiagnosisController {

    @Autowired
    private GejalaService gejalaService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private PenyakitService penyakitService;

    @Autowired
    private PasienService pasienService;

    @GetMapping("/diagnosis")
    public String getDiagnosis(Model model, HttpServletRequest httpServletRequest, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        Integer pageNo = Integer.valueOf(httpServletRequest.getParameter("page"));
        model.addAttribute("title","RS || Diagnosis");
        model.addAttribute("gejala",gejalaService.listGejala());
        Pasiens pasienByEmail = pasienService.findByEmail(principal.getName());
        Page<Diagnosis> diagnosisByIdPasiens = diagnosisService.getAllDiagnosisByIdPasiens(pageNo,pasienByEmail.getId());
        if(diagnosisByIdPasiens.isEmpty()){
            model.addAttribute("noDg","Anda Belum Pernah Melakakuan Diagnosis Apapun");
        }
        model.addAttribute("totalPages",diagnosisByIdPasiens.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("diagnosis",diagnosisByIdPasiens);
        return "diagnosis";
    }

    @PostMapping(value = "/diagnosis")
    public String saveDiagnosis(@RequestParam("gejalaListCheck")List<String> gejalaCheck, Principal principal){
        Diagnosis diagnosis = diagnosisService.saveDiagnosis(principal.getName());
        List<Penyakit> penyakit = penyakitService.getPenyakit();
        for (Penyakit penyakit1 : penyakit) {
            DetailDiagnosisDto detailDiagnosisDto = diagnosisService.detailDiagnosis(penyakit1, gejalaCheck);
            DetailDiagnosis detailDiagnosis = new DetailDiagnosis();
            detailDiagnosis.setDiagnosis(diagnosis);
            detailDiagnosis.setHasilDiagnosis(detailDiagnosisDto.getHasilDiagnosis());
            detailDiagnosis.setPenyakit(penyakit1);
            diagnosisService.saveDetailDiagnosis(detailDiagnosis);
        }
        return "redirect:/diagnosis?page=0";
    }

    @GetMapping(value = "/detail")
    public String detailDiagnosis(Model model,HttpServletRequest httpServletRequest,Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String id = httpServletRequest.getParameter("id");
        String username = principal.getName();
        Pasiens pasiens = pasienService.findByEmail(username);
        List<String> penyakitList = new ArrayList<>();
        List<Double> bobotList = new ArrayList<>();
        Diagnosis diagnosisByIdPasienAndIdDiagnosis = diagnosisService.getDiagnosisByIdPasienAndIdDiagnosis(id, pasiens.getId(), penyakitList, bobotList);
        Penyakit penyakit = diagnosisService.bobotTerbersar(diagnosisByIdPasienAndIdDiagnosis);
        Double bobotBesar = diagnosisService.bobotValue(diagnosisByIdPasienAndIdDiagnosis);
        model.addAttribute("penyakitList",penyakitList);
        model.addAttribute("title","RS || Details");
        model.addAttribute("bobotList",bobotList);
        model.addAttribute("id",id);
        model.addAttribute("penyakit",penyakit);
        model.addAttribute("boboBesar",bobotBesar);
        model.addAttribute("diagnosis",diagnosisByIdPasienAndIdDiagnosis);
        return "detail-diagnosis";
    }

    @GetMapping("/print")
    public String detailDiagnosisPrint(Model model,HttpServletRequest httpServletRequest,Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String id = httpServletRequest.getParameter("id");
        String username = principal.getName();
        Pasiens pasiens = pasienService.findByEmail(username);
        List<String> penyakitList = new ArrayList<>();
        List<Double> bobotList = new ArrayList<>();
        Diagnosis diagnosisByIdPasienAndIdDiagnosis = diagnosisService.getDiagnosisByIdPasienAndIdDiagnosis(id, pasiens.getId(), penyakitList, bobotList);
        Penyakit penyakit = diagnosisService.bobotTerbersar(diagnosisByIdPasienAndIdDiagnosis);
        Double bobotBesar = diagnosisService.bobotValue(diagnosisByIdPasienAndIdDiagnosis);
        model.addAttribute("penyakitList",penyakitList);
        model.addAttribute("title","RS || Print-Details");
        model.addAttribute("bobotList",bobotList);
        model.addAttribute("penyakit",penyakit);
        model.addAttribute("boboBesar",bobotBesar);
        model.addAttribute("diagnosis",diagnosisByIdPasienAndIdDiagnosis);
        return "print-detail-diagnosis";
    }

}
