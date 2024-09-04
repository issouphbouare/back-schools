package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Paiement;
import com.mas.school.repository.EleveRepository;
import com.mas.school.repository.PaiementRepository;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;
    @Autowired
    private EleveRepository eleveRepository;


    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    public Paiement createPaiement(Paiement paiement) {
    	
    	paiement.getEleve().setSolde(paiement.getEleve().getSolde()-paiement.getMontant());
    	eleveRepository.save(paiement.getEleve());
        return paiementRepository.save(paiement);
    }

    public Paiement updatePaiement(Long id, Paiement paiementDetails) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvée pour id : " + id));
        paiement.setMontant(paiementDetails.getMontant());
        paiement.setMois(paiementDetails.getMois());
        paiement.setEleve(paiementDetails.getEleve());
        
        paiement.getEleve().setSolde(paiement.getEleve().getSolde()+paiement.getMontant()-paiementDetails.getMontant());
    	eleveRepository.save(paiement.getEleve());
    	
        return paiementRepository.save(paiement);
    }

    public void deletePaiement(Long id) {
    	Paiement paiement= paiementRepository.findById(id).get();
    	paiement.getEleve().setSolde(paiement.getEleve().getSolde()+paiement.getMontant());
    	eleveRepository.save(paiement.getEleve());
    	
        paiementRepository.deleteById(id);
    }
    
    
    public Page<Paiement> search(String searchTerm, String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        
        // Retourner les résultats de la recherche avec tri et pagination
        return paiementRepository.searchByKeywordInAllColumns(searchTerm, annee, pageable);
    }

}

