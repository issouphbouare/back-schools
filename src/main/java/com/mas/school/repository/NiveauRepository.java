package com.mas.school.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Niveau;
import com.mas.school.model.Serie;

public interface NiveauRepository extends JpaRepository<Niveau, Long> {
	
	@Query("SELECT a FROM Niveau a WHERE " +
	           "a.libelle LIKE %:keyword% OR " +
	           "a.serie.cycle.libelle LIKE %:keyword% OR " +
	           "a.serie.libelle LIKE %:keyword% ")
Page<Niveau> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);

	List<Niveau> findBySerie(Serie serie);
}
