package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Depense;
public interface DepenseRepository extends JpaRepository<Depense, Long> {
	@Query("SELECT a FROM Depense a WHERE " +
			   "( a.libelle LIKE %:keyword% OR " +
			   "TO_CHAR(a.dateDepense, 'DD-MM-YYYY') LIKE %:keyword%) AND "+
			   "a.anneeScolaire.libelle LIKE %:annee%")
	 Page<Depense> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);

}
