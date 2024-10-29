package com.mas.school.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public String generateNomClasse(Classe classe) {
		List<Classe>  classes=classeRepository.findByNiveauAndAnneeScolaire(classe.getNiveau(), classe.getAnneeScolaire());
    	long seq=0;
    	for (Classe c : classes) seq++;
    	if(classe.getNiveau().getLibelle().equals("00")) classe.getNiveau().setLibelle("Jardin");
    	if(classe.getNiveau().getLibelle().equals("01")) classe.getNiveau().setLibelle("1ere");
    	if(classe.getNiveau().getLibelle().equals("02")) classe.getNiveau().setLibelle("2eme");
    	if(classe.getNiveau().getLibelle().equals("03")) classe.getNiveau().setLibelle("3eme");
    	if(classe.getNiveau().getLibelle().equals("04")) classe.getNiveau().setLibelle("4eme");
    	if(classe.getNiveau().getLibelle().equals("05")) classe.getNiveau().setLibelle("5eme");
    	if(classe.getNiveau().getLibelle().equals("06")) classe.getNiveau().setLibelle("6eme");
    	if(classe.getNiveau().getLibelle().equals("07")) classe.getNiveau().setLibelle("7eme");
    	if(classe.getNiveau().getLibelle().equals("08")) classe.getNiveau().setLibelle("8eme");
    	if(classe.getNiveau().getLibelle().equals("09")) classe.getNiveau().setLibelle("9eme");
    	if(classe.getNiveau().getLibelle().equals("10")) classe.getNiveau().setLibelle("10eme");
    	if(classe.getNiveau().getLibelle().equals("11")) classe.getNiveau().setLibelle("11eme");
    	if(classe.getNiveau().getLibelle().equals("12")) classe.getNiveau().setLibelle("12eme");
    	
		return classe.getNiveau().getLibelle()+"_"+classe.getNiveau().getSerie().getRef()+"_"+generateAlphabet(seq)+"_"+classe.getAnneeScolaire().getRef();
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
