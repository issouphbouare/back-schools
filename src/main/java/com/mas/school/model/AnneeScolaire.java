package com.mas.school.model;




import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AnneeScolaire {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(unique = true)
	private String libelle;
	@Column(unique = true)
	private String ref;
	
	@JsonIgnore
	@OneToMany(mappedBy = "anneeScolaire", cascade = CascadeType.ALL)
	private List<Classe> classes;
	
	@JsonIgnore
	@OneToMany(mappedBy = "anneeScolaire", cascade = CascadeType.ALL)
	private List<Enseignant> enseignants;
	
	@JsonIgnore
	@OneToMany(mappedBy = "anneeScolaire", cascade = CascadeType.ALL)
	private List<Depense> depenses;
	
	@JsonIgnore
	@OneToMany(mappedBy = "anneeScolaire", cascade = CascadeType.ALL)
	private List<AutreEntree> autreEntrees;

}
