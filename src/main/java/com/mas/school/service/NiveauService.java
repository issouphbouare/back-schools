package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Niveau;
import com.mas.school.repository.NiveauRepository;
import com.mas.school.repository.SerieRepository;

@Service
public class NiveauService {

    @Autowired
    private NiveauRepository niveauRepository;
    @Autowired
    private SerieRepository serieRepository;

    public List<Niveau> getAllNiveaus() {
        return niveauRepository.findAll();
    }

    public Optional<Niveau> getNiveauById(Long id) {
        return niveauRepository.findById(id);
    }

    public Niveau createNiveau(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

    public Niveau updateNiveau(Long id, Niveau niveauDetails) {
        Niveau niveau = niveauRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Niveau non trouvée pour id : " + id));
        niveau.setLibelle(niveauDetails.getLibelle());
        //niveau.setSerie(niveauDetails.getSerie());
        return niveauRepository.save(niveau);
    }

    public void deleteNiveau(Long id) {
        niveauRepository.deleteById(id);
    }
    
    public Page<Niveau> search(String searchTerm, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return niveauRepository.searchByKeywordInAllColumns(searchTerm, pageable);
    }

	public List<Niveau> getBySerie(Long id) {
		// TODO Auto-generated method stub
		return niveauRepository.findBySerie(serieRepository.findById(id).get());
	}
    
    
}

