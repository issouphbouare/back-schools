package com.mas.school.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mas.school.model.AnneeScolaire;
import com.mas.school.model.Classe;
import com.mas.school.model.Eleve;
import com.mas.school.repository.ClasseRepository;
import com.mas.school.repository.EleveRepository;
@Service
public class GeneratorService {
	@Autowired
	private ClasseRepository classeRepository;
	
	@Autowired
	private EleveRepository eleveRepository;
	
	// Generation de nom de classe
	public String generateNomClasse(String niveau, AnneeScolaire annee) {
		List<Classe>  classes=classeRepository.findByNiveauAndAnneeScolaire(niveau, annee);
    	long seq=0;
    	for (Classe classe : classes) seq++;
    	
    	if(niveau.equals("00")) niveau="Jardin";
    	if(niveau.equals("01")) niveau="1ere";
    	if(niveau.equals("02")) niveau="2eme";
    	if(niveau.equals("03")) niveau="3eme";
    	if(niveau.equals("04")) niveau="4eme";
    	if(niveau.equals("05")) niveau="5eme";
    	if(niveau.equals("06")) niveau="6eme";
    	if(niveau.equals("07")) niveau="7eme";
    	if(niveau.equals("08")) niveau="8eme";
    	if(niveau.equals("09")) niveau="9eme";
    	if(niveau.equals("10")) niveau="10eme";
    	if(niveau.equals("11")) niveau="11eme";
    	if(niveau.equals("12")) niveau="12eme";
    	
		return niveau+"_"+generateAlphabet(seq)+"_"+annee.getRef();
	} 
	
	private String generateAlphabet(long seq) {
	    // Vérifie si le nombre est valide (entre 1 et 26)
	    if (seq < 0 || seq > 25) {
	        throw new IllegalArgumentException("Le nombre doit être entre 1 et 26");
	    }
	    char letter = (char) ('A' + (seq));
	    return String.valueOf(letter);
	}
	
	// Générer des codes de "00A" à "99Z"
    private List<String> generateCodes() {
        List<String> codes = new ArrayList<>();
        for (int i = 0; i <= 99; i++) {
            for (char c = 'A'; c <= 'Z'; c++) {
                codes.add(String.format("%02d%c", i, c));
            }
        }
        //Collections.shuffle(codes); // Mélanger la liste
        return codes;
    }

    // Générer un numéro unique combinant l'année de naissance, le code de commune, le genre et un code
    public String generateMatricule(Eleve eleve) {
    	List<Eleve>  eleves=eleveRepository.findByCleAndClasse(eleve.getCle(), eleve.getClasse());
    	int seq=0;
    	for (Eleve eleve1 : eleves) seq++;
    	
        List<String> codes = generateCodes();
        // Sélectionner le premier code de la liste mélangée 25999
        String code = codes.get(seq);
        // Combiner la cle et le numero de sequence de 000A a 999Z
        String matricule = String.format("%s%s", eleve.getCle(), code);
        return matricule;
    }
    
}
