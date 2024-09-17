package com.mas.school.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Enseignant {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String nom;
	private String prenom;
	
	@Column(unique = true, nullable = false)
	private String code;
	@Column(nullable = false, unique = true)
	private String telephone;
	private String genre;
	private String typeEnseignant;
	private double impayes;
	private double tauxHoraire;
	private double tauxForfaitaire;
	
	@ManyToOne
	private AnneeScolaire anneeScolaire;
	
	@JsonIgnore
	@OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
	private List<Remuneration> remunerations;
	
	@JsonIgnore
	@OneToMany(mappedBy = "enseignant", cascade = CascadeType.ALL)
	private List<Seance> seances;

	public void appliquerMisAjour_12() {
		if(typeEnseignant.equals("Permanent_12/12")) this.impayes += this.tauxForfaitaire;
		
	}
	
	public void appliquerMisAjour_10() {
		if(typeEnseignant.equals("Permanent_10/12")) this.impayes += this.tauxForfaitaire;
		
	}
	public void appliquerMisAjour() {
		if(!typeEnseignant.equals("Vacataire")) this.impayes += this.tauxForfaitaire;
		
	}

}
