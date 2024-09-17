package com.mas.school.model;




import java.util.List;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
