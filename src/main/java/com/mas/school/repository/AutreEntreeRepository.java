package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.AutreEntree;

public interface AutreEntreeRepository extends JpaRepository<AutreEntree, Long> {
	@Query("SELECT a FROM AutreEntree a WHERE " +
			   "a.libelle LIKE %:keyword% AND " +
			   "a.anneeScolaire.libelle LIKE %:annee% OR " +
			   "TO_CHAR(a.dateEntree, 'DD-MM-YYYY') LIKE %:keyword% ")
	 Page<AutreEntree> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);


}
