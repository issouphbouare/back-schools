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

import com.mas.school.model.Seance;
import com.mas.school.service.SeanceService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/seances")
public class SeanceController {

    @Autowired
    private SeanceService seanceService;

    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceService.getAllSeances();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seance> getSeanceById(@PathVariable(value = "id") Long id) {
        Optional<Seance> seance = seanceService.getSeanceById(id);
        if (seance.isPresent()) {
            return ResponseEntity.ok().body(seance.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Seance createSeance(@RequestBody Seance seance) {
        return seanceService.createSeance(seance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seance> updateSeance(@PathVariable(value = "id") Long id, @RequestBody Seance seanceDetails) {
        return ResponseEntity.ok(seanceService.updateSeance(id, seanceDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeance(@PathVariable(value = "id") Long id) {
        seanceService.deleteSeance(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Seance>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Seance> seances = seanceService.search(keyword,annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(seances);
    }
}
