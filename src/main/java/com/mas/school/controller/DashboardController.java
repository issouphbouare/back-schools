package com.mas.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mas.school.model.DetailCaisse;
import com.mas.school.model.NombreEleve;
import com.mas.school.model.NombrePersonnel;
import com.mas.school.model.Retard_Paiement_Remuneration;
import com.mas.school.service.DashboardService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/dashboard")
public class DashboardController {
	@Autowired
    private DashboardService dashboardService;
	
	@GetMapping("/personnels")
    public NombrePersonnel effectifPersonnels() {
        return dashboardService.NB_Personnel();
    }
	
	@GetMapping("/eleves")
    public NombreEleve effectifEleves() {
        return dashboardService.NB_Eleve();
    }
	
	@GetMapping("/caisses")
    public DetailCaisse caisse() {
        return dashboardService.getCaisseEcole();
    }
	
	@GetMapping("/retards")
    public Retard_Paiement_Remuneration retard() {
        return dashboardService.GetEn_retard();
    }

}
