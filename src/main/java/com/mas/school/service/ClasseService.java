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
import com.mas.school.model.Classe;
import com.mas.school.repository.ClasseRepository;

@Service
public class ClasseService {

    @Autowired
    private ClasseRepository classeRepository;
    
    @Autowired
    private GeneratorService generatorService;

    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    public Optional<Classe> getClasseById(Long id) {
        return classeRepository.findById(id);
    }

    public Classe createClasse(Classe classe) {
    	classe.setNom(generateNomClasse(classe.getNiveau(),classe.getAnneeScolaire()));
        return classeRepository.save(classe);
    }

    private String generateNomClasse(String niveau, AnneeScolaire annee) {
		// TODO Auto-generated method stub
		return generatorService.generateNomClasse(niveau, annee);
	}

	public Classe updateClasse(Long id, Classe classeDetails) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée pour id : " + id));
        classe.setNom(generateNomClasse(classeDetails.getNiveau(),classeDetails.getAnneeScolaire()));
        classe.setNiveau(classeDetails.getNiveau());
        classe.setCycle(classeDetails.getCycle());
        classe.setAnneeScolaire(classeDetails.getAnneeScolaire());
        return classeRepository.save(classe);
    }

    public void deleteClasse(Long id) {
        classeRepository.deleteById(id);
    }
    
    public Page<Classe> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return classeRepository.searchByKeywordInAllColumns(searchTerm, annee, pageable);
    }
}

