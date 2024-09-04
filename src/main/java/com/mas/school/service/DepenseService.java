package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Depense;
import com.mas.school.repository.DepenseRepository;

@Service
public class DepenseService {

    @Autowired
    private DepenseRepository depenseRepository;

    public List<Depense> getAllDepenses() {
        return depenseRepository.findAll();
    }

    public Optional<Depense> getDepenseById(Long id) {
        return depenseRepository.findById(id);
    }

    public Depense createDepense(Depense depense) {
        return depenseRepository.save(depense);
    }

    public Depense updateDepense(Long id, Depense depenseDetails) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Depense non trouvée pour id : " + id));
        depense.setLibelle(depenseDetails.getLibelle());
        depense.setMontant(depenseDetails.getMontant());
        depense.setAnneeScolaire(depenseDetails.getAnneeScolaire());
        return depenseRepository.save(depense);
    }

    public void deleteDepense(Long id) {
        depenseRepository.deleteById(id);
    }
    
    public Page<Depense> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return depenseRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
}

