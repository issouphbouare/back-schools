package com.mas.school.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mas.school.model.Cycle;
import com.mas.school.model.Serie;
import com.mas.school.repository.CycleRepository;
import com.mas.school.repository.SerieRepository;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;
    @Autowired
    private CycleRepository cycleRepository;

    public List<Serie> getAllSeries() {
        return serieRepository.findAll();
    }

    public Optional<Serie> getSerieById(Long id) {
        return serieRepository.findById(id);
    }

    public Serie createSerie(Serie serie) {
        return serieRepository.save(serie);
    }

    public Serie updateSerie(Long id, Serie serieDetails) {
        Serie serie = serieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serie non trouvée pour id : " + id));
        serie.setLibelle(serieDetails.getLibelle());
        serie.setRef(serieDetails.getRef());
        //serie.setCycle(serieDetails.getCycle());
        return serieRepository.save(serie);
    }

    public void deleteSerie(Long id) {
        serieRepository.deleteById(id);
    }
    
    public Page<Serie> search(String searchTerm, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return serieRepository.searchByKeywordInAllColumns(searchTerm, pageable);
    }

	public List<Serie> getByCycle(Long id) {
		// TODO Auto-generated method stub
		return serieRepository.findByCycle(cycleRepository.findById(id).get());
	}
    
    
}

