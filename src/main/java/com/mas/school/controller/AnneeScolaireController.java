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

import com.mas.school.model.AnneeScolaire;
import com.mas.school.service.AnneeScolaireService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/anneescolaires")
public class AnneeScolaireController {

    @Autowired
    private AnneeScolaireService anneeScolaireService;

    @GetMapping
    public List<AnneeScolaire> getAllAnneeScolaires() {
        return anneeScolaireService.getAllAnneeScolaires();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnneeScolaire> getAnneeScolaireById(@PathVariable(value = "id") Long id) {
        Optional<AnneeScolaire> anneeScolaire = anneeScolaireService.getAnneeScolaireById(id);
        if (anneeScolaire.isPresent()) {
            return ResponseEntity.ok().body(anneeScolaire.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public AnneeScolaire createAnneeScolaire(@RequestBody AnneeScolaire anneeScolaire) {
        return anneeScolaireService.createAnneeScolaire(anneeScolaire);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnneeScolaire> updateAnneeScolaire(@PathVariable(value = "id") Long id, @RequestBody AnneeScolaire anneeScolaireDetails) {
        return ResponseEntity.ok(anneeScolaireService.updateAnneeScolaire(id, anneeScolaireDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnneeScolaire(@PathVariable(value = "id") Long id) {
        anneeScolaireService.deleteAnneeScolaire(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<AnneeScolaire>> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<AnneeScolaire> anneeScolaires = anneeScolaireService.search(keyword, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(anneeScolaires);
    }
    
    @GetMapping("/courant")
    public AnneeScolaire getDerniereAnneeScolaire() {
        return anneeScolaireService.getDerniereAnneeScolaire();
    }
}

