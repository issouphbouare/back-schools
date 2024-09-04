package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.AnneeScolaire;

public interface AnneeScolaireRepository extends JpaRepository<AnneeScolaire, Long> {
	
	@Query("SELECT a FROM AnneeScolaire a WHERE " +
	           "a.libelle LIKE %:keyword% OR " +
	           "a.ref LIKE %:keyword%")
Page<AnneeScolaire> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);
	// Requête pour récupérer le dernier enregistrement basé sur l'ID
    @Query("SELECT a FROM AnneeScolaire a ORDER BY a.id DESC")
    AnneeScolaire findLastAnneeScolaire();
}
