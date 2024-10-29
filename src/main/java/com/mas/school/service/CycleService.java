package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Cycle;
import com.mas.school.repository.CycleRepository;

@Service
public class CycleService {

    @Autowired
    private CycleRepository cycleRepository;

    public List<Cycle> getAllCycles() {
        return cycleRepository.findAll();
    }

    public Optional<Cycle> getCycleById(Long id) {
        return cycleRepository.findById(id);
    }

    public Cycle createCycle(Cycle cycle) {
        return cycleRepository.save(cycle);
    }

    public Cycle updateCycle(Long id, Cycle cycleDetails) {
        Cycle cycle = cycleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cycle non trouvée pour id : " + id));
        cycle.setLibelle(cycleDetails.getLibelle());
        return cycleRepository.save(cycle);
    }

    public void deleteCycle(Long id) {
        cycleRepository.deleteById(id);
    }
    
    public Page<Cycle> search(String searchTerm, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return cycleRepository.searchByKeywordInAllColumns(searchTerm, pageable);
    }
    
    
}

