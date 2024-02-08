package com.project.es.data.admin.controller;

import com.project.es.data.libary.dto.GejalaDto;
import com.project.es.data.libary.dto.PenyakitDto;
import com.project.es.data.libary.entity.Gejala;
import com.project.es.data.libary.entity.Penyakit;
import com.project.es.data.libary.service.GejalaService;
import com.project.es.data.libary.service.PenyakitService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@Slf4j
public class PenyakitController {

    @Autowired
    private GejalaService gejalaService;

    @Autowired
    private PenyakitService penyakitService;

    @GetMapping(value = "/penyakit/new")
    public String addPenyakit(Model model, Principal principal, @Param("numberGejala") String numberGejala,RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        if(numberGejala == null){
            redirectAttributes.addFlashAttribute("errors","You Cannot Modify Endpoint/Routing");
            return "redirect:/penyakit";
        }
        model.addAttribute("title","Admin | Penyakit");
        model.addAttribute("gejala",gejalaService.listGejala());
        model.addAttribute("counter",Integer.parseInt(numberGejala));
        model.addAttribute("picturesError",false);
        model.addAttribute("penyakitDto",new PenyakitDto());
        return "Penyakit/add_penyakit";
    }

    @PostMapping(value = "/penyakit/new")
    private String savePenyakit(@Valid @ModelAttribute("penyakitDto") PenyakitDto penyakitDto,
                                  BindingResult bindingResult, @RequestParam(name = "gejalaList") Set<String> gejala,@RequestParam(name = "counter") String counter,@RequestParam("pictures") MultipartFile pictures,Model model, RedirectAttributes redirectAttributes){
        try {
            if (bindingResult.hasFieldErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                System.out.println(fieldError.getField());
                model.addAttribute("penyakitDto",penyakitDto);
                model.addAttribute("gejala",gejalaService.listGejala());
                model.addAttribute("counter",Integer.parseInt(counter));
                System.out.println("Binding Result Has Errors");
                if (pictures.isEmpty()){
                    model.addAttribute("picturesError",true);
                }else {
                    model.addAttribute("picturesError",false);
                }
                model.addAttribute("title","Admin | Penyakit");
                return "Penyakit/add_penyakit";
            }

            if(pictures.isEmpty()){
                model.addAttribute("penyakitDto",penyakitDto);
                model.addAttribute("gejala",gejalaService.listGejala());
                model.addAttribute("counter",Integer.parseInt(counter));
                model.addAttribute("picturesError",true);
                model.addAttribute("title","Admin | Penyakit");
                return "Penyakit/add_penyakit";
            }
            String namaPenyakit = penyakitDto.getNamaPenyakit();
            Penyakit penyakit = penyakitService.findPenyakit(namaPenyakit);
            if (penyakit != null) {
                model.addAttribute("errors", "Penyakit yang anda inputkan sudah ada di dalam table");
                model.addAttribute("penyakitDto",penyakitDto);
                model.addAttribute("gejala",gejalaService.listGejala());
                model.addAttribute("title","Admin | Penyakit");
                model.addAttribute("counter",Integer.parseInt(counter));
                return "Penyakit/add_penyakit";
            }
            List<Gejala> gejalaList = new ArrayList<>();
            Double totalBobot = 0.0;
            for (String s : gejala) {
                Gejala gejalaTemp = gejalaService.searchGejalaById(s);
                totalBobot+=gejalaTemp.getBobot();
                gejalaList.add(gejalaTemp);
            }
            penyakitDto.setTotal_bobot(totalBobot);
            penyakitDto.setGejalaList(gejalaList);
            penyakitService.savePenyakit(penyakitDto,pictures);
            redirectAttributes.addFlashAttribute("success","Penyakit Baru Telah Berhasil Di Simpan");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("penyakitDto",penyakitDto);
            model.addAttribute("counter",Integer.parseInt(counter));
            model.addAttribute("errors", "Ada Masalah Dengan Server Coba lagi nanti");
            model.addAttribute("gejala",gejalaService.listGejala());
            model.addAttribute("title","Admin | Penyakit");
            return "Penyakit/add_penyakit";
        }
        return "redirect:/penyakit";
    }

    @GetMapping(value = "/penyakit")
    public String getPenyakit(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","Admin | Penyakit");
        model.addAttribute("penyakit",penyakitService.getPenyakit());
        return "Penyakit/index";
    }

    @GetMapping("/penyakit/delete/{id}")
    public String deletePenyakit(@PathVariable("id") String idPenyakit,Principal principal,RedirectAttributes redirectAttributes){
        if(principal == null){
            return "redirect:/login";
        }
        PenyakitDto penyakitDto = penyakitService.findPenyakitDto(idPenyakit);
        if(penyakitDto == null){
            redirectAttributes.addFlashAttribute("errors","Id Penyakit Tidak Ditemukan");
        }
        try{
            penyakitService.deletePenyakit(idPenyakit);
            redirectAttributes.addFlashAttribute("success","Penyakit Berhasil dihapus");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errors","Field ini masih mempunyai dependency dengan table lain");
        }
        return "redirect:/penyakit";
    }

    @GetMapping("/penyakit/update/{id}")
    public String updatePenyakit(@PathVariable("id") String idPenyakit,Model model,Principal principal,RedirectAttributes redirectAttributes){
        if (principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title","Admin | Penyakit");
        model.addAttribute("gejala",gejalaService.listGejala());
        try {
            PenyakitDto penyakitDto = penyakitService.findPenyakitDto(idPenyakit);
            if(penyakitDto == null){
                redirectAttributes.addFlashAttribute("errors","Cannot Find ID");
                return "redirect:/penyakit";
            }
            model.addAttribute("penyakitDto",penyakitDto);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errors","Cannot Find ID");
            return "redirect:/penyakit";
        }
        return "Penyakit/edit_penyakit";
    }

    @PostMapping(value = "/penyakit/update")
    private String updatePenyakit(@RequestParam(name = "gejalaList") Set<String> gejala,@Valid @ModelAttribute("penyakitDto") PenyakitDto penyakitDto,@RequestParam("pictures") MultipartFile pictures,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        try {
            if (bindingResult.hasErrors()) {
                model.addAttribute("penyakitDto",penyakitDto);
                model.addAttribute("gejala",gejalaService.listGejala());
                model.addAttribute("title","Admin | Penyakit");
                return "Penyakit/edit_penyakit";
            }
            List<Gejala> gejalaList = new ArrayList<>();
            Double totalBobot = 0.0;
            for (String s : gejala) {
                Gejala gejalaTemp = gejalaService.searchGejalaById(s);
                totalBobot+=gejalaTemp.getBobot();
                gejalaList.add(gejalaTemp);
            }
            penyakitDto.setTotal_bobot(totalBobot);
            penyakitDto.setGejalaList(gejalaList);
            penyakitService.updatePenyakit(penyakitDto,pictures);
            redirectAttributes.addFlashAttribute("success","Penyakit Telah Berhasil Di Update");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("penyakitDto",penyakitDto);
            model.addAttribute("errors", "Ada Masalah Dengan Server Coba lagi nanti");
            model.addAttribute("gejala",gejalaService.listGejala());
            model.addAttribute("title","Admin | Penyakit");
            return "Penyakit/edit_penyakit";
        }
        return "redirect:/penyakit";
    }
}