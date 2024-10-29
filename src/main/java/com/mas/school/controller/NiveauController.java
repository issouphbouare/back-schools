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

import com.mas.school.model.Niveau;
import com.mas.school.model.Serie;
import com.mas.school.service.NiveauService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/niveaux")
public class NiveauController {

    @Autowired
    private NiveauService niveauService;

    @GetMapping
    public List<Niveau> getAllNiveaus() {
        return niveauService.getAllNiveaus();
    }
    
    @GetMapping("/bySerie/{id}")
    public List<Niveau> getNiveauxBySerie(@PathVariable(value = "id") Long id) {
        return niveauService.getBySerie(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable(value = "id") Long id) {
        Optional<Niveau> niveau = niveauService.getNiveauById(id);
        if (niveau.isPresent()) {
            return ResponseEntity.ok().body(niveau.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Niveau createNiveau(@RequestBody Niveau niveau) {
        return niveauService.createNiveau(niveau);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Niveau> updateNiveau(@PathVariable(value = "id") Long id, @RequestBody Niveau niveauDetails) {
        return ResponseEntity.ok(niveauService.updateNiveau(id, niveauDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable(value = "id") Long id) {
        niveauService.deleteNiveau(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Niveau>> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Niveau> niveaus = niveauService.search(keyword, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(niveaus);
    }
    
}

