package com.mas.school.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Cycle;
import com.mas.school.model.Serie;

public interface SerieRepository extends JpaRepository<Serie, Long> {
	
	@Query("SELECT a FROM Serie a WHERE " +
	           "a.libelle LIKE %:keyword% OR " +
	           "a.cycle.libelle LIKE %:keyword% OR " +
	           "a.ref LIKE %:keyword%")
Page<Serie> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);

	List<Serie> findByCycle(Cycle cycle);

	
}
