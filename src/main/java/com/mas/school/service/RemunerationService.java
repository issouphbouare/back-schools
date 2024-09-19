package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Remuneration;
import com.mas.school.repository.EnseignantRepository;
import com.mas.school.repository.RemunerationRepository;

@Service
public class RemunerationService {

    @Autowired
    private RemunerationRepository remunerationRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Remuneration> getAllRemunerations() {
        return remunerationRepository.findAll();
    }

    public Optional<Remuneration> getRemunerationById(Long id) {
        return remunerationRepository.findById(id);
    }

    public Remuneration createRemuneration(Remuneration remuneration) {
    	remuneration.getEnseignant().setImpayes(remuneration.getEnseignant().getImpayes()-remuneration.getMontant());
    	enseignantRepository.save(remuneration.getEnseignant());
        return remunerationRepository.save(remuneration);
    }

    public Remuneration updateRemuneration(Long id, Remuneration remunerationDetails) {
        Remuneration remuneration = remunerationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Remuneration non trouvée pour id : " + id));
        remuneration.getEnseignant().setImpayes(remuneration.getEnseignant().getImpayes()+remuneration.getMontant()-remunerationDetails.getMontant());
        remuneration.setMontant(remunerationDetails.getMontant());
        remuneration.setMois(remunerationDetails.getMois());
        //remuneration.setEnseignant(remunerationDetails.getEnseignant());
        
        remuneration.getEnseignant().setImpayes(remuneration.getEnseignant().getImpayes()+remuneration.getMontant()-remunerationDetails.getMontant());
    	enseignantRepository.save(remuneration.getEnseignant());
    	
        return remunerationRepository.save(remuneration);
    }

    public void deleteRemuneration(Long id) {
    	Remuneration remuneration=remunerationRepository.findById(id).get();
    	remuneration.getEnseignant().setImpayes(remuneration.getEnseignant().getImpayes()+remuneration.getMontant());
    	enseignantRepository.save(remuneration.getEnseignant());
    	
        remunerationRepository.deleteById(id);
    }
    
    public Page<Remuneration> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return remunerationRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
}

