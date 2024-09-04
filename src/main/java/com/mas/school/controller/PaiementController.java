package com.mas.school.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mas.school.model.Paiement;
import com.mas.school.service.PaiementService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable(value = "id") Long id) {
        Optional<Paiement> paiement = paiementService.getPaiementById(id);
        if (paiement.isPresent()) {
            return ResponseEntity.ok().body(paiement.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Paiement createPaiement(@RequestBody Paiement paiement) {
        return paiementService.createPaiement(paiement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable(value = "id") Long id, @RequestBody Paiement paiementDetails) {
        return ResponseEntity.ok(paiementService.updatePaiement(id, paiementDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable(value = "id") Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Paiement>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        // Appel du service avec les nouveaux paramètres de tri
        Page<Paiement> paiements = paiementService.search(keyword, annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(paiements);
    }

}
