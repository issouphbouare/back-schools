package com.mas.school.repository;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mas.school.model.Classe;
import com.mas.school.model.Enseignant;
import com.mas.school.model.Seance;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
	@Query("SELECT a FROM Seance a WHERE " +
			   "( a.matiere LIKE %:keyword% OR " +
			   "a.horaire LIKE %:keyword% OR " +
			   "a.mois LIKE %:keyword% OR " +
			   "a.classe.nom LIKE %:keyword% OR " +
			   "TO_CHAR(a.dateSeance, 'DD-MM-YYYY') LIKE %:keyword% ) AND "+
			   "(a.enseignant.telephone LIKE %:personnel% OR "+
			    "a.enseignant.prenom LIKE %:personnel% OR "+
			    "a.enseignant.nom LIKE %:personnel% OR "+
			    "a.enseignant.code LIKE %:personnel% ) AND " +
			   "a.enseignant.anneeScolaire.libelle LIKE %:annee% ")
	 Page<Seance> searchByKeywordInAllColumns(@Param("keyword") String keyword, @Param("annee") String annee,@Param("personnel") String personnel, Pageable pageable);

	

	boolean existsByClasseAndHeureDebutAndDateSeance(Classe classe, LocalTime heureDebut, Date date);

	boolean existsByEnseignantAndHeureDebutAndDateSeance(Enseignant enseignant, LocalTime heureDebut, Date date);

}
