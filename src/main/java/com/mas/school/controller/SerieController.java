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
import com.mas.school.model.Serie;
import com.mas.school.service.SerieService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<Serie> getAllSeries() {
        return serieService.getAllSeries();
    }
    
    @GetMapping("/byCycle/{id}")
    public List<Serie> getSeriesByCycle(@PathVariable(value = "id") Long id) {
        return serieService.getByCycle(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable(value = "id") Long id) {
        Optional<Serie> serie = serieService.getSerieById(id);
        if (serie.isPresent()) {
            return ResponseEntity.ok().body(serie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Serie createSerie(@RequestBody Serie serie) {
        return serieService.createSerie(serie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Serie> updateSerie(@PathVariable(value = "id") Long id, @RequestBody Serie serieDetails) {
        return ResponseEntity.ok(serieService.updateSerie(id, serieDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSerie(@PathVariable(value = "id") Long id) {
        serieService.deleteSerie(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Serie>> search(
            @RequestParam String keyword,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,  // Paramètre pour spécifier la colonne de tri
            @RequestParam(required = false, defaultValue = "DESC") String sortDirection  // Paramètre pour spécifier la direction de tri
    ) {
        Page<Serie> series = serieService.search(keyword, page, size, sortBy, sortDirection);
        return ResponseEntity.ok(series);
    }
    
}

