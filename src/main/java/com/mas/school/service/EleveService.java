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

import com.mas.school.model.Eleve;
import com.mas.school.repository.ClasseRepository;
import com.mas.school.repository.EleveRepository;



@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private GeneratorService generatorService;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> getEleveById(Long id) {
        return eleveRepository.findById(id);
    }

    public Eleve createEleve(Eleve eleve) {
    	eleve.setCle(generateCle(eleve));
    	eleve.setMatricule(generateMatricule(eleve));
    	eleve.setSolde(eleve.getSolde()-eleve.getInscription()-eleve.getRelicat()-eleve.getScolarite());
        return eleveRepository.save(eleve);
    }

    private String generateMatricule(Eleve eleve) {
		// TODO Auto-generated method stub
		return generatorService.generateMatricule(eleve);
	}

	private String generateCle(Eleve eleve) {
		
		String code1 = eleve.getClasse().getNom().substring(eleve.getClasse().getNom().length() - 4);
		String code2 = eleve.getGenre().equals("Fille") ? "F" : "G";
		String code3=eleve.getClasse().getNiveau();
		String code4=eleve.getNom().substring(0, 2).toUpperCase();
		
		String result = code1+code2+code3+code4;
		return result;
	}

	public Eleve updateEleve(Long id, Eleve eleveDetails) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve non trouvée pour id : " + id));
        
        eleve.setNom(eleveDetails.getNom());
        eleve.setPrenom(eleveDetails.getPrenom());
        eleve.setGenre(eleveDetails.getGenre());
        eleve.setDateNaissance(eleveDetails.getDateNaissance());
        eleve.setLieuNaissance(eleveDetails.getLieuNaissance());
        eleve.setNomTuteur(eleveDetails.getNomTuteur());
        eleve.setTelTuteur(eleveDetails.getTelTuteur());
        
        eleve.setSolde(eleve.getSolde()-eleveDetails.getInscription()-eleveDetails.getRelicat()-eleveDetails.getScolarite()
        		+eleve.getInscription()+eleve.getRelicat()+eleve.getScolarite());
        
        eleve.setInscription(eleveDetails.getInscription());
        eleve.setScolarite(eleveDetails.getScolarite());
        eleve.setRelicat(eleveDetails.getRelicat());
        eleve.setMensualite(eleveDetails.getMensualite());
       
        eleve.setClasse(eleveDetails.getClasse());
        return eleveRepository.save(eleve);
    }

    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
    
    public Page<Eleve> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return eleveRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
    
 // Méthode planifiée pour exécuter à la fin de chaque mois
    @Transactional 
    @Scheduled(cron = "0 0 0 25 10-12,1-6 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void MiseAjourSolde() {
        List<Eleve> eleves = eleveRepository.findAll();

        for (Eleve eleve : eleves) {
            eleve.appliquerMisAjour();
            eleveRepository.save(eleve);
        }
    }
}

