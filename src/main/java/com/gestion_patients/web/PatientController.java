package com.gestion_patients.web;


import com.gestion_patients.entities.Patients;
import com.gestion_patients.repositories.PatientsRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Binding;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientsRepository patientsRepository;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword",defaultValue = "") String keyword){
//        Page<Patients> pagePatients = patientsRepository.findAll(PageRequest.of(page, size));
        Page<Patients> pagePatients = patientsRepository.findByNameContains(keyword,PageRequest.of(page, size));
        model.addAttribute("listPatient", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",keyword);
        return "patient";
    }

    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword, int page){
        patientsRepository.deleteById(id);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin/patients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public List<Patients> listPastients(){
        return patientsRepository.findAll();
    }


    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatients(Model model){
        model.addAttribute("patients", new Patients());
        return "formPatients";
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(Model model, @Valid Patients patients, BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "0") int  page){
        if (bindingResult.hasErrors()) return "formPatients";
        patientsRepository.save(patients);
        return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }


    @GetMapping("/admin/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatients(Model model, Long id, String keyword, int page){
        Patients patients = patientsRepository.findById(id).orElse(null);
        if (patients == null) throw new RuntimeException("no patients with that ID");
        model.addAttribute("patients", patients);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editPatients";
    }


}
