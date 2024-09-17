package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Remuneration;

public interface RemunerationRepository extends JpaRepository<Remuneration, Long> {
	@Query("SELECT a FROM Remuneration a WHERE " +
			   "( a.mois LIKE %:keyword% OR " +
			   "a.enseignant.telephone LIKE %:keyword% OR " +
			   "a.enseignant.prenom LIKE %:keyword% OR " +
			   "a.enseignant.nom LIKE %:keyword% OR " +
			   "TO_CHAR(a.dateRemuneration, 'DD-MM-YYYY') LIKE %:keyword% ) AND "+
			   "a.enseignant.anneeScolaire.libelle LIKE %:annee% ")
	 Page<Remuneration> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);

}
