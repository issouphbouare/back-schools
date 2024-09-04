package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Seance;
import com.mas.school.repository.EnseignantRepository;
import com.mas.school.repository.SeanceRepository;

@Service
public class SeanceService {

    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Optional<Seance> getSeanceById(Long id) {
        return seanceRepository.findById(id);
    }

    public Seance createSeance(Seance seance) {
    	seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes()+seance.getNombreHeure()*seance.getEnseignant().getTauxHoraire());
    	enseignantRepository.save(seance.getEnseignant());
    	
        return seanceRepository.save(seance);
    }

    public Seance updateSeance(Long id, Seance seanceDetails) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seance non trouvée pour id : " + id));
        seance.setNombreHeure(seanceDetails.getNombreHeure());
        seance.setMatiere(seanceDetails.getMatiere());
        seance.setHeureDebut(seanceDetails.getHeureDebut());
        seance.setHeureFin(seanceDetails.getHeureFin());
        seance.setEnseignant(seanceDetails.getEnseignant());
        seance.setClasse(seanceDetails.getClasse());
        
        seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes()-seance.getNombreHeure()*seance.getEnseignant().getTauxHoraire()+seance.getNombreHeure()*seanceDetails.getEnseignant().getTauxHoraire());
    	enseignantRepository.save(seance.getEnseignant());
    	
        return seanceRepository.save(seance);
    }

    public void deleteSeance(Long id) {
    	Seance seance=seanceRepository.findById(id).get();
    	seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes()-seance.getNombreHeure()*seance.getEnseignant().getTauxHoraire());
    	enseignantRepository.save(seance.getEnseignant());
        seanceRepository.deleteById(id);
    }
    
    public Page<Seance> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return seanceRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
}

