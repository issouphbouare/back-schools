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

import com.mas.school.model.Eleve;
import com.mas.school.service.EleveService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/eleves")
public class EleveController {

    @Autowired
    private EleveService eleveService;

    @GetMapping
    public List<Eleve> getAllEleves() {
        return eleveService.getAllEleves();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Eleve> getEleveById(@PathVariable(value = "id") Long id) {
        Optional<Eleve> eleve = eleveService.getEleveById(id);
        if (eleve.isPresent()) {
            return ResponseEntity.ok().body(eleve.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Eleve createEleve(@RequestBody Eleve eleve) {
        return eleveService.createEleve(eleve);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Eleve> updateEleve(@PathVariable(value = "id") Long id, @RequestBody Eleve eleveDetails) {
        return ResponseEntity.ok(eleveService.updateEleve(id, eleveDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEleve(@PathVariable(value = "id") Long id) {
        eleveService.deleteEleve(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Eleve>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Eleve> eleves = eleveService.search(keyword, annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(eleves);
    }
}
