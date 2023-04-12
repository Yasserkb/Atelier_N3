package com.gestion_patients.web;


import com.gestion_patients.entities.Patients;
import com.gestion_patients.repositories.PatientsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientsRepository patientsRepository;

    @GetMapping(path = "/index")
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

    @GetMapping("/delete")
    public String delete(Long id, String keyword, int page){
        patientsRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Patients> listPastients(){
        return patientsRepository.findAll();
    }

}
