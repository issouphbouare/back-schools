package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.AnneeScolaire;
import com.mas.school.repository.AnneeScolaireRepository;

@Service
public class AnneeScolaireService {

    @Autowired
    private AnneeScolaireRepository anneeScolaireRepository;

    public List<AnneeScolaire> getAllAnneeScolaires() {
        return anneeScolaireRepository.findAll();
    }

    public Optional<AnneeScolaire> getAnneeScolaireById(Long id) {
        return anneeScolaireRepository.findById(id);
    }

    public AnneeScolaire createAnneeScolaire(AnneeScolaire anneeScolaire) {
    	anneeScolaire.setRef(anneeScolaire.getLibelle().substring(2, 4) + anneeScolaire.getLibelle().substring(7));
        return anneeScolaireRepository.save(anneeScolaire);
    }

    public AnneeScolaire updateAnneeScolaire(Long id, AnneeScolaire anneeScolaireDetails) {
        AnneeScolaire anneeScolaire = anneeScolaireRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AnneeScolaire non trouvée pour id : " + id));
        anneeScolaire.setLibelle(anneeScolaireDetails.getLibelle());
        return anneeScolaireRepository.save(anneeScolaire);
    }

    public void deleteAnneeScolaire(Long id) {
        anneeScolaireRepository.deleteById(id);
    }
    
    public Page<AnneeScolaire> search(String searchTerm, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return anneeScolaireRepository.searchByKeywordInAllColumns(searchTerm, pageable);
    }
    
    public AnneeScolaire getDerniereAnneeScolaire() {
        // Utilise la méthode du repository pour obtenir le dernier enregistrement
        return anneeScolaireRepository.findLastAnneeScolaire();
    }
}

