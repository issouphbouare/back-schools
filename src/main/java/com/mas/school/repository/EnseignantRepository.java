package com.mas.school.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.AnneeScolaire;
import com.mas.school.model.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
	@Query("SELECT a FROM Enseignant a WHERE " +
			   "( a.telephone LIKE %:keyword% OR " +
			   "a.nom LIKE %:keyword% OR " +
			   "a.prenom LIKE %:keyword% OR " +
			   "a.genre LIKE %:keyword% OR " +
			   "a.typeEnseignant LIKE %:keyword% ) AND " +
			   "a.anneeScolaire.libelle LIKE %:annee%")
	 Page<Enseignant> searchByKeywordInAllColumns(@Param("keyword") String keyword,@Param("annee") String annee, Pageable pageable);
 
	public List<Enseignant> findByAnneeScolaire(AnneeScolaire anneeScolaire);
}
