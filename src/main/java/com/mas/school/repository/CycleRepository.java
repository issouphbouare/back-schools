package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Cycle;

public interface CycleRepository extends JpaRepository<Cycle, Long> {
	
	@Query("SELECT a FROM Cycle a WHERE " +
	           "a.libelle LIKE %:keyword% ")
Page<Cycle> searchByKeywordInAllColumns(@Param("keyword") String keyword, Pageable pageable);
}
