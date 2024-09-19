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

public class Paiement {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private double montant;
	private String type;
	private String mois;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  datePaiement;
	
	@ManyToOne
	private Eleve eleve;

	@ManyToOne
	private User user;
}
