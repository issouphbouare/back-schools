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

import com.mas.school.model.Classe;
import com.mas.school.service.ClasseService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/classes")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @GetMapping
    public List<Classe> getAllClasses() {
        return classeService.getAllClasses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Classe> getClasseById(@PathVariable(value = "id") Long id) {
        Optional<Classe> classe = classeService.getClasseById(id);
        if (classe.isPresent()) {
            return ResponseEntity.ok().body(classe.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Classe createClasse(@RequestBody Classe classe) {
        return classeService.createClasse(classe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Classe> updateClasse(@PathVariable(value = "id") Long id, @RequestBody Classe classeDetails) {
        return ResponseEntity.ok(classeService.updateClasse(id, classeDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable(value = "id") Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Classe>> search(
            @RequestParam String keyword,
            @RequestParam String annee,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Classe> classes = classeService.search(keyword, annee, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(classes);
    }
}
