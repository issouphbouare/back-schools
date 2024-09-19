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

public class Remuneration {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String mois;
	private double Montant;
	@Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE DEFAULT CURRENT_DATE", insertable = false, updatable = false)
	private Date  dateRemuneration;
	
	@ManyToOne
	private Enseignant enseignant;
	
	@ManyToOne
	private User user;

}
