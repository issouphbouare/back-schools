package com.mas.school.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Eleve {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(unique = true, nullable = false)
	private String matricule;
	private String nom;
	private String prenom;
	private String genre;
	private Date dateNaissance;
	private String lieuNaissance;
	private String nomTuteur;
	private String telTuteur;
	private double solde;
	private String cle;
	private double inscription;
	private double mensualite;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  DateInscription;
	
	@ManyToOne
	private Classe classe;
	
	@JsonIgnore
	@OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL)
	private List<Paiement> paiements;

	public void appliquerMisAjour() {
		 this.solde -= this.mensualite;
		
	}
}
