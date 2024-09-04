package com.mas.school.model;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(unique = true, nullable = false)
	private String nom;
	private String cycle;
	private String niveau;
	
	@ManyToOne
	private AnneeScolaire anneeScolaire;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
	private List<Eleve> eleves;
	
	@JsonIgnore
	@OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
	private List<Seance> seances;
}
