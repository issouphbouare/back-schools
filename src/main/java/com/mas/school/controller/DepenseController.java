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

import com.mas.school.model.Depense;
import com.mas.school.service.DepenseService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/depenses")
public class DepenseController {

    @Autowired
    private DepenseService depenseService;

    @GetMapping
    public List<Depense> getAllDepenses() {
        return depenseService.getAllDepenses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Depense> getDepenseById(@PathVariable(value = "id") Long id) {
        Optional<Depense> depense = depenseService.getDepenseById(id);
        if (depense.isPresent()) {
            return ResponseEntity.ok().body(depense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Depense createDepense(@RequestBody Depense depense) {
        return depenseService.createDepense(depense);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Depense> updateDepense(@PathVariable(value = "id") Long id, @RequestBody Depense depenseDetails) {
        return ResponseEntity.ok(depenseService.updateDepense(id, depenseDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepense(@PathVariable(value = "id") Long id) {
        depenseService.deleteDepense(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Depense>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Depense> depenses = depenseService.search(keyword,annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(depenses);
    }
}
