package com.mas.school.model;

import java.util.Date;

import javax.persistence.*;

import com.mas.school.jwtSwagger.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AutreEntree {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String libelle;
	private double Montant;
	
	@ManyToOne
	private AnneeScolaire anneeScolaire;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  dateEntree;
	
	@ManyToOne
	private User user;
}
