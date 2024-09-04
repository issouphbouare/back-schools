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

import com.mas.school.model.Enseignant;
import com.mas.school.service.EnseignantService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/enseignants")
public class EnseignantController {

    @Autowired
    private EnseignantService enseignantService;

    @GetMapping
    public List<Enseignant> getAllEnseignants() {
        return enseignantService.getAllEnseignants();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable(value = "id") Long id) {
        Optional<Enseignant> enseignant = enseignantService.getEnseignantById(id);
        if (enseignant.isPresent()) {
            return ResponseEntity.ok().body(enseignant.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Enseignant createEnseignant(@RequestBody Enseignant enseignant) {
        return enseignantService.createEnseignant(enseignant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable(value = "id") Long id, @RequestBody Enseignant enseignantDetails) {
        return ResponseEntity.ok(enseignantService.updateEnseignant(id, enseignantDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable(value = "id") Long id) {
        enseignantService.deleteEnseignant(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Enseignant>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Enseignant> enseignants = enseignantService.search(keyword, annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(enseignants);
    }
}
