package com.mas.school.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.AnneeScolaire;
import com.mas.school.model.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
	
	@Query("SELECT a FROM Classe a WHERE " +
			   "( a.nom LIKE %:keyword% OR " +
			   "a.niveau LIKE %:keyword% OR "+
			   "a.cycle LIKE %:keyword% ) AND " +
			   "a.anneeScolaire.libelle LIKE %:annee% ")
	 Page<Classe> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);

	List<Classe> findByNiveauAndAnneeScolaire(String niveau, AnneeScolaire annee);

}
