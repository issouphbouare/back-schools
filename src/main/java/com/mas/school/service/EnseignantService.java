package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mas.school.model.Enseignant;
import com.mas.school.repository.EnseignantRepository;



@Service
public class EnseignantService {

    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Enseignant> getAllEnseignants() {
        return enseignantRepository.findAll();
    }

    public Optional<Enseignant> getEnseignantById(Long id) {
        return enseignantRepository.findById(id);
    }

    public Enseignant createEnseignant(Enseignant enseignant) {
    	enseignant.setCode(enseignant.getAnneeScolaire().getRef() + enseignant.getTelephone());
        return enseignantRepository.save(enseignant);
    }

    public Enseignant updateEnseignant(Long id, Enseignant enseignantDetails) {
        Enseignant enseignant = enseignantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvée pour id : " + id));
        
        enseignant.setCode(enseignantDetails.getAnneeScolaire().getRef() + enseignantDetails.getTelephone());
        enseignant.setNom(enseignantDetails.getNom());
        enseignant.setPrenom(enseignantDetails.getPrenom());
        enseignant.setGenre(enseignantDetails.getGenre());
        enseignant.setTelephone(enseignantDetails.getTelephone());
        enseignant.setTypeEnseignant(enseignantDetails.getTypeEnseignant());
        enseignant.setAnneeScolaire(enseignantDetails.getAnneeScolaire());
        enseignant.setTauxHoraire(enseignantDetails.getTauxHoraire());
        enseignant.setAnneeScolaire(enseignantDetails.getAnneeScolaire());
        return enseignantRepository.save(enseignant);
    }

    public void deleteEnseignant(Long id) {
        enseignantRepository.deleteById(id);
    }
    
    public Page<Enseignant> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return enseignantRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
    
 // Méthode planifiée pour exécuter à la fin de chaque mois
    @Transactional 
    @Scheduled(cron = "0 0 0 25 10-12,1-6 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void MiseAjourSolde() {
        List<Enseignant> enseignants = enseignantRepository.findAll();

        for (Enseignant enseignant : enseignants) {
            enseignant.appliquerMisAjour();
            enseignantRepository.save(enseignant);
        }
    }
    
    @Transactional 
    @Scheduled(cron = "0 0 0 25 7 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void MiseAjourSolde_10() {
        List<Enseignant> enseignants = enseignantRepository.findAll();

        for (Enseignant enseignant : enseignants) {
            enseignant.appliquerMisAjour();
            enseignantRepository.save(enseignant);
        }
    }
    
    @Transactional 
    @Scheduled(cron = "0 0 0 25 7-9 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void MiseAjourSolde_12() {
        List<Enseignant> enseignants = enseignantRepository.findAll();

        for (Enseignant enseignant : enseignants) {
            enseignant.appliquerMisAjour();
            enseignantRepository.save(enseignant);
        }
    }

}

