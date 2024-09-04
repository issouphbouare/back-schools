package com.mas.school.model;

import java.sql.Date;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Seance {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String matiere;
	private LocalTime heureDebut;
    private LocalTime heureFin;
	private double nombreHeure;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  dateSeance;
	
	@ManyToOne
	private Enseignant enseignant;
	
	@ManyToOne
	private Classe classe;

}
