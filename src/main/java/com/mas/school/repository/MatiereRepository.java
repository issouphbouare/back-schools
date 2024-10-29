package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Matiere;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
	
	@Query("SELECT a FROM Matiere a WHERE " +
	           "a.libelle LIKE %:keyword% OR " +
	           "a.ref LIKE %:keyword%")
Page<Matiere> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);
	// Requête pour récupérer le dernier enregistrement basé sur l'ID
    @Query("SELECT a FROM Matiere a ORDER BY a.id DESC")
    Matiere findLastMatiere();
}
