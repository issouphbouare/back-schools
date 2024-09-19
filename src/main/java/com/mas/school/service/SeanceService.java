package com.mas.school.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
        // Vérifier si une séance avec la même heure de début existe pour la même classe
        boolean existeSeanceClasse = seanceRepository.existsByClasseAndHeureDebutAndDateSeance(seance.getClasse(), seance.getHeureDebut(), new Date());
        // Vérifier si une séance avec la même heure de début existe pour le même enseignant
        boolean existeSeanceEnseignant = seanceRepository.existsByEnseignantAndHeureDebutAndDateSeance(seance.getEnseignant(), seance.getHeureDebut(), new Date());

        if (existeSeanceClasse || existeSeanceEnseignant) {
            throw new IllegalArgumentException("Une séance à la même heure existe déjà pour cette classe ou cet enseignant.");
        }

        // Ajout des impayés à l'enseignant
        seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes() + seance.getNombreHeure() * seance.getEnseignant().getTauxHoraire());
        enseignantRepository.save(seance.getEnseignant());
        
        // Ajout du mois courant
        LocalDate currentDate = LocalDate.now();
        String currentMonth = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        seance.setMois(currentMonth);
        
        // Calcul de l'horaire
        LocalTime heureDebut = seance.getHeureDebut();
        int nombreHeure = seance.getNombreHeure();
        LocalTime heureFin = heureDebut.plusHours(nombreHeure);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        String horaire = heureDebut.format(formatter) + " - " + heureFin.format(formatter);
        
        seance.setHoraire(horaire);
        
        return seanceRepository.save(seance);
    }


    public Seance updateSeance(Long id, Seance seanceDetails) {
        Seance seance = seanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seance non trouvée pour id : " + id));
        seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes()-seance.getNombreHeure()*seance.getEnseignant().getTauxHoraire()+seanceDetails.getNombreHeure()*seance.getEnseignant().getTauxHoraire());
        seance.setNombreHeure(seanceDetails.getNombreHeure());
        seance.setMatiere(seanceDetails.getMatiere());
        seance.setHoraire(seanceDetails.getHoraire());
        //seance.setEnseignant(seanceDetails.getEnseignant());
        seance.setClasse(seanceDetails.getClasse());
        
       
    	enseignantRepository.save(seance.getEnseignant());
    	
        return seanceRepository.save(seance);
    }

    public void deleteSeance(Long id) {
    	Seance seance=seanceRepository.findById(id).get();
    	seance.getEnseignant().setImpayes(seance.getEnseignant().getImpayes()-seance.getNombreHeure()*seance.getEnseignant().getTauxHoraire());
    	enseignantRepository.save(seance.getEnseignant());
        seanceRepository.deleteById(id);
    }
    
    public Page<Seance> search(String searchTerm,String annee, String personnel, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return seanceRepository.searchByKeywordInAllColumns(searchTerm,annee,personnel, pageable);
    }
}

