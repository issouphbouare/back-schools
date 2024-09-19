package com.mas.school.jwtSwagger;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mas.school.model.AutreEntree;
import com.mas.school.model.Depense;
import com.mas.school.model.Paiement;
import com.mas.school.model.Remuneration;
import com.mas.school.model.Seance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
    @Table(name = "users")
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;


        @Column(unique = true)
        private String username;

        @NotNull
        @Column(unique = true)
        private String email;

        @NotNull
        @Column()
        private String password;
        
        @JsonIgnore
    	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    	private List<Seance> seances;
        
        @JsonIgnore
    	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    	private List<Paiement> paiements;
        
        @JsonIgnore
    	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    	private List<Remuneration> remunerations;
        
        @JsonIgnore
    	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    	private List<Depense> depenses;
        
        @JsonIgnore
    	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    	private List<AutreEntree> entrees;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(  name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();
    }