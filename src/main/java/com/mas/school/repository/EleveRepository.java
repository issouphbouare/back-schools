package com.mas.school.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.AnneeScolaire;
import com.mas.school.model.Classe;
import com.mas.school.model.Eleve;

public interface EleveRepository extends JpaRepository<Eleve, Long> {
	@Query("SELECT a FROM Eleve a WHERE " +
			   "( a.matricule LIKE %:keyword% OR " +
			   "a.nom LIKE %:keyword% OR " +
			   "a.prenom LIKE %:keyword% OR " +
			   "a.genre LIKE %:keyword% OR " +
			   "TO_CHAR(a.dateNaissance, 'DD-MM-YYYY') LIKE %:keyword% OR " +
			   "a.lieuNaissance LIKE %:keyword% OR " +
			   "a.nomTuteur LIKE %:keyword% OR " +
			   "a.telTuteur LIKE %:keyword% OR " +
			   "a.classe.nom LIKE %:keyword% ) AND " +
			   "a.classe.anneeScolaire.libelle LIKE %:annee%  ")
	 Page<Eleve> searchByKeywordInAllColumns(@Param("keyword") String keyword,@Param("annee") String annee, Pageable pageable);

	

	List<Eleve> findByCleAndClasse(String cle, Classe classe);
	
	List<Eleve> findByClasse_AnneeScolaire(AnneeScolaire anneeScolaire);

}
