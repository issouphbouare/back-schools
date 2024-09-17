package com.mas.school.model;

import java.util.Date;
import java.time.LocalTime;

import javax.persistence.*;
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
