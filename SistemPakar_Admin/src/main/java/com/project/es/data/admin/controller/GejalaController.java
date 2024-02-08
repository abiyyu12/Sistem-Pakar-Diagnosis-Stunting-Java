package com.project.es.data.admin.controller;

import com.project.es.data.libary.dto.GejalaDto;
import com.project.es.data.libary.entity.Admin;
import com.project.es.data.libary.entity.Gejala;
import com.project.es.data.libary.service.GejalaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class GejalaController {

    @Autowired
    private GejalaService gejalaService;

    @GetMapping(value = "/gejala")
    public String getGejala(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("gejala",gejalaService.listGejala());
        model.addAttribute("title","Admin | Gejala");
        return "Gejala/index";
    }

    @GetMapping(value = "/gejala/new")
    public String addGejala(Model model,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("gejalaDto",new GejalaDto());
        model.addAttribute("title","Admin | Gejala");
        return "Gejala/add_gejala";
    }

    @PostMapping(value = "/gejala/new")
    public String saveGejala(@Valid @ModelAttribute("gejalaDto") GejalaDto gejalaDto,
                             BindingResult result,
                             Model model, RedirectAttributes redirectAttributes){
        try {
            if (result.hasErrors()) {
                model.addAttribute("gejalaDto", gejalaDto);
                model.addAttribute("title","Admin | Gejala");
                return "Gejala/add_gejala";
            }
            String namaGejala = gejalaDto.getNamaGejala();
            Gejala gejala = gejalaService.findByNamaGejala(namaGejala);
            if (gejala != null) {
                model.addAttribute("errors", "Gejala yang anda inputkan sudah ada di dalam table");
                model.addAttribute("gejalaDto", gejalaDto);
                model.addAttribute("title","Admin | Gejala");
                return "Gejala/add_gejala";
            }
            gejalaService.addGejala(gejalaDto);
            redirectAttributes.addFlashAttribute("success","Gejala Baru Berhasil Di Tambah");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Terjadi Masalah Pada Server Coba lagi nanti");
            model.addAttribute("title","Admin | Gejala");
            return "Gejala/add_gejala";
        }
        return "redirect:/gejala";
    }

    @GetMapping(value = "/gejala/update/{id}")
    public String editGejala(@PathVariable("id") String idGejala,Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("gejalaDto",gejalaService.findGejalaById(idGejala));
        model.addAttribute("title","Admin | Gejala");
        return "Gejala/edit_gejala";
    }

    @PostMapping(value = "gejala/update")
    public String updateGejala(@Valid @ModelAttribute("gejalaDto") GejalaDto gejalaDto,
                             BindingResult result,
                             Model model, RedirectAttributes redirectAttributes){
        try {
            if (result.hasErrors()) {
                model.addAttribute("gejalaDto", gejalaDto);
                model.addAttribute("title","Admin | Gejala");
                return "Gejala/edit_gejala";
            }
            gejalaService.updateGejala(gejalaDto);
            redirectAttributes.addFlashAttribute("success","Gejala Telah Berhasil Di Ubah");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Terjadi Masalah Pada Server Coba lagi nanti");
            model.addAttribute("title","Admin | Gejala");
            return "Gejala/edit_gejala";
        }
        return "redirect:/gejala";
    }

    @GetMapping("/gejala/delete/{id}")
    public String deleteGejala(@PathVariable("id") String id,RedirectAttributes redirectAttributes,Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        GejalaDto gejalaById = gejalaService.findGejalaById(id);
        if(gejalaById == null){
            redirectAttributes.addFlashAttribute("errors","Id Gejala Tidak Ditemukan");
        }
        try{
            gejalaService.deleteGejala(id);
            redirectAttributes.addFlashAttribute("success","Gejala Berhasil dihapus");
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("errors","Field ini masih mempunyai dependency dengan table lain");
        }
        return "redirect:/gejala";
    }
}
