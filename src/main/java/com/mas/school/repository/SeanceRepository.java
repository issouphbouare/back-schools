package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Seance;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
	@Query("SELECT a FROM Seance a WHERE " +
			   "( a.matiere LIKE %:keyword% OR " +
			   "TO_CHAR(a.heureDebut,'HH24:MI:SS') LIKE %:keyword% OR " + 
			   "TO_CHAR(a.heureFin,'HH24:MI:SS') LIKE %:keyword% OR " +
			   "a.enseignant.telephone LIKE %:keyword% OR " +
			   "a.enseignant.prenom LIKE %:keyword% OR " +
			   "a.enseignant.nom LIKE %:keyword% OR " +
			   "a.classe.nom LIKE %:keyword% OR " +
			   "TO_CHAR(a.dateSeance, 'DD-MM-YYYY') LIKE %:keyword% ) AND " +
			   "a.enseignant.anneeScolaire.libelle LIKE %:annee% ")
	 Page<Seance> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);

}
