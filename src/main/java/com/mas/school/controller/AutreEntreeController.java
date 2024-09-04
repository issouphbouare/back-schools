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

import com.mas.school.model.AutreEntree;
import com.mas.school.service.AutreEntreeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/autreEntrees")
public class AutreEntreeController {

    @Autowired
    private AutreEntreeService autreEntreeService;

    @GetMapping
    public List<AutreEntree> getAllAutreEntrees() {
        return autreEntreeService.getAllAutreEntrees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutreEntree> getAutreEntreeById(@PathVariable(value = "id") Long id) {
        Optional<AutreEntree> autreEntree = autreEntreeService.getAutreEntreeById(id);
        if (autreEntree.isPresent()) {
            return ResponseEntity.ok().body(autreEntree.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public AutreEntree createAutreEntree(@RequestBody AutreEntree autreEntree) {
        return autreEntreeService.createAutreEntree(autreEntree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutreEntree> updateAutreEntree(@PathVariable(value = "id") Long id, @RequestBody AutreEntree autreEntreeDetails) {
        return ResponseEntity.ok(autreEntreeService.updateAutreEntree(id, autreEntreeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutreEntree(@PathVariable(value = "id") Long id) {
        autreEntreeService.deleteAutreEntree(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<AutreEntree>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<AutreEntree> autreEntrees = autreEntreeService.search(keyword,annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(autreEntrees);
    }
}
