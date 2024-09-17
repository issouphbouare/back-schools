package com.mas.school.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mas.school.model.AutreEntree;
import com.mas.school.model.Depense;
import com.mas.school.model.DetailCaisse;
import com.mas.school.model.Eleve;
import com.mas.school.model.Enseignant;
import com.mas.school.model.NombreEleve;
import com.mas.school.model.NombrePersonnel;
import com.mas.school.model.Paiement;
import com.mas.school.model.Remuneration;
import com.mas.school.model.Retard_Paiement_Remuneration;
import com.mas.school.repository.AnneeScolaireRepository;
import com.mas.school.repository.AutreEntreeRepository;
import com.mas.school.repository.ClasseRepository;
import com.mas.school.repository.DepenseRepository;
import com.mas.school.repository.EleveRepository;
import com.mas.school.repository.EnseignantRepository;
import com.mas.school.repository.PaiementRepository;
import com.mas.school.repository.RemunerationRepository;

@Service
public class DashboardService {
	@Autowired
	EleveRepository eleveRepository;
	@Autowired
	EnseignantRepository enseignantRepository;
	@Autowired
	ClasseRepository classeRepository;
	@Autowired
	DepenseRepository depenseRepository;
	@Autowired
	PaiementRepository paiementRepository;
	@Autowired
	AutreEntreeRepository autreEntreeRepository;
	@Autowired
	RemunerationRepository remunerationRepository;
	@Autowired
	AnneeScolaireRepository anneeScolaireRepository;
	
	public long GetPersonal() {
		return enseignantRepository.count();
	}
	public long GetEleve() {
		return eleveRepository.count();
	}
	
	public Retard_Paiement_Remuneration GetEn_retard() {
		Retard_Paiement_Remuneration retard_Paiement_Remuneration=new Retard_Paiement_Remuneration();
		List<Eleve> eleves=eleveRepository.findByClasse_AnneeScolaire(anneeScolaireRepository.findLastAnneeScolaire());;
		double somme1=0;
		for(Eleve eleve : eleves) somme1+=eleve.getSolde();
		
		List<Enseignant> enseignants=enseignantRepository.findByAnneeScolaire(anneeScolaireRepository.findLastAnneeScolaire());
		double somme2=0;
		for(Enseignant enseignant : enseignants) somme2+=enseignant.getImpayes();
		
		retard_Paiement_Remuneration.setMensualiteEleve(somme1);
		retard_Paiement_Remuneration.setRemunerationPersonnel(somme2);
		
		return retard_Paiement_Remuneration;
	}
	
	
	
	public DetailCaisse getCaisseEcole() {
		DetailCaisse caisse= new DetailCaisse();
		List<Paiement> paiements=paiementRepository.findAll();
		List<AutreEntree> autreEntrees=autreEntreeRepository.findAll();
		List<Remuneration> remunerations=remunerationRepository.findAll();
		List<Depense> depenses=depenseRepository.findAll();
		
		double somme1=0;
		for(Paiement paiement : paiements) somme1+=paiement.getMontant();
		double somme2=0;
		for(AutreEntree autreEntree : autreEntrees) somme2+=autreEntree.getMontant();
		double somme3=0;
		for(Remuneration remuneration : remunerations) somme3+=remuneration.getMontant();
		double somme4=0;
		for(Depense depense : depenses) somme4+=depense.getMontant();
		caisse.setEntree(somme1+somme2);
		caisse.setSortie(somme3+somme4);
		caisse.setSolde(caisse.getEntree()-caisse.getSortie());
		return caisse;
		
	}
	
	public NombrePersonnel NB_Personnel() {
		NombrePersonnel nombrePersonnel=new NombrePersonnel();
		List<Enseignant> enseignants=enseignantRepository.findByAnneeScolaire(anneeScolaireRepository.findLastAnneeScolaire());
		long nbHomme=0;
		long nbFemme=0;
		for(Enseignant enseignant : enseignants) {
			if(enseignant.getGenre().equals("Homme")) nbHomme+=1;
			else nbFemme+=1;
		}
		nombrePersonnel.setHomme(nbHomme);
		nombrePersonnel.setFemme(nbFemme);
		nombrePersonnel.setTotal(nbHomme+nbFemme);
		return nombrePersonnel;
		
	}
	
	public NombreEleve NB_Eleve() {
		NombreEleve nombreEleve=new NombreEleve();
		List<Eleve> eleves=eleveRepository.findByClasse_AnneeScolaire(anneeScolaireRepository.findLastAnneeScolaire());
		long nbHomme=0;
		long nbFemme=0;
		for(Eleve eleve : eleves) {
			if(!eleve.getGenre().equals("Fille")) nbHomme+=1;
			else nbFemme+=1;
		}
		nombreEleve.setHomme(nbHomme);
		nombreEleve.setFemme(nbFemme);
		nombreEleve.setTotal(nbHomme+nbFemme);
		return nombreEleve;
		
	}

}
