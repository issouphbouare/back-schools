package com.mas.school.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Niveau {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String libelle;
@ManyToOne
private Serie serie;

@JsonIgnore
@OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL)
private List<Classe> classes;

}
