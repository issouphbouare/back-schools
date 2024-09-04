package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.AutreEntree;
import com.mas.school.repository.AutreEntreeRepository;

@Service
public class AutreEntreeService {

    @Autowired
    private AutreEntreeRepository autreEntreeRepository;

    public List<AutreEntree> getAllAutreEntrees() {
        return autreEntreeRepository.findAll();
    }

    public Optional<AutreEntree> getAutreEntreeById(Long id) {
        return autreEntreeRepository.findById(id);
    }

    public AutreEntree createAutreEntree(AutreEntree autreEntree) {
        return autreEntreeRepository.save(autreEntree);
    }

    public AutreEntree updateAutreEntree(Long id, AutreEntree autreEntreeDetails) {
        AutreEntree autreEntree = autreEntreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AutreEntree non trouvée pour id : " + id));
        autreEntree.setLibelle(autreEntreeDetails.getLibelle());
        autreEntree.setMontant(autreEntreeDetails.getMontant());
        autreEntree.setAnneeScolaire(autreEntreeDetails.getAnneeScolaire());
        return autreEntreeRepository.save(autreEntree);
    }

    public void deleteAutreEntree(Long id) {
        autreEntreeRepository.deleteById(id);
    }
    
    public Page<AutreEntree> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return autreEntreeRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
}

