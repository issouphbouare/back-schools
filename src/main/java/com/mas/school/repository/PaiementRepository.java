package com.mas.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
	@Query("SELECT a FROM Paiement a WHERE " +
			   "( a.type LIKE %:keyword% OR " +
			   "a.mois LIKE %:keyword% OR " +
			   "a.eleve.matricule LIKE %:keyword% OR " +
			   "a.eleve.prenom LIKE %:keyword% OR " +
			   "a.eleve.nom LIKE %:keyword% OR " +
			   "a.eleve.telTuteur LIKE %:keyword% OR " +
			   "TO_CHAR(a.datePaiement, 'DD-MM-YYYY') LIKE %:keyword%) AND "+
			   "a.eleve.classe.anneeScolaire.libelle LIKE %:annee% ")
	 Page<Paiement> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee, Pageable pageable);

}
