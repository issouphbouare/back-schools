package com.mas.school.model;

import java.sql.Date;

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

public class Paiement {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private double Montant;
	private String typePaiement;
	private String mois;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  datePaiement;
	
	@ManyToOne
	private Eleve eleve;


}
