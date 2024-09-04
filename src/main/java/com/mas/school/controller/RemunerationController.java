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

import com.mas.school.model.Remuneration;
import com.mas.school.service.RemunerationService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/remunerations")
public class RemunerationController {

    @Autowired
    private RemunerationService remunerationService;

    @GetMapping
    public List<Remuneration> getAllRemunerations() {
        return remunerationService.getAllRemunerations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Remuneration> getRemunerationById(@PathVariable(value = "id") Long id) {
        Optional<Remuneration> remuneration = remunerationService.getRemunerationById(id);
        if (remuneration.isPresent()) {
            return ResponseEntity.ok().body(remuneration.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Remuneration createRemuneration(@RequestBody Remuneration remuneration) {
        return remunerationService.createRemuneration(remuneration);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Remuneration> updateRemuneration(@PathVariable(value = "id") Long id, @RequestBody Remuneration remunerationDetails) {
        return ResponseEntity.ok(remunerationService.updateRemuneration(id, remunerationDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRemuneration(@PathVariable(value = "id") Long id) {
        remunerationService.deleteRemuneration(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Remuneration>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Remuneration> remunerations = remunerationService.search(keyword,annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(remunerations);
    }
}
