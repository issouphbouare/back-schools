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

import com.mas.school.model.Cycle;
import com.mas.school.service.CycleService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cycles")
public class CycleController {

    @Autowired
    private CycleService cycleService;

    @GetMapping
    public List<Cycle> getAllCycles() {
        return cycleService.getAllCycles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cycle> getCycleById(@PathVariable(value = "id") Long id) {
        Optional<Cycle> cycle = cycleService.getCycleById(id);
        if (cycle.isPresent()) {
            return ResponseEntity.ok().body(cycle.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Cycle createCycle(@RequestBody Cycle cycle) {
        return cycleService.createCycle(cycle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cycle> updateCycle(@PathVariable(value = "id") Long id, @RequestBody Cycle cycleDetails) {
        return ResponseEntity.ok(cycleService.updateCycle(id, cycleDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCycle(@PathVariable(value = "id") Long id) {
        cycleService.deleteCycle(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Cycle>> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Cycle> cycles = cycleService.search(keyword, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(cycles);
    }
    
}

