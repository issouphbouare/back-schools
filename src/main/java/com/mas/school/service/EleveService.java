package com.mas.school.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mas.school.model.Classe;
import com.mas.school.model.Eleve;
import com.mas.school.repository.ClasseRepository;
import com.mas.school.repository.EleveRepository;



@Service
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private GeneratorService generatorService;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Optional<Eleve> getEleveById(Long id) {
        return eleveRepository.findById(id);
    }

    public Eleve createEleve(Eleve eleve) {
    	eleve.setCle(generateCle(eleve));
    	eleve.setMatricule(generateMatricule(eleve));
    	eleve.setNomTuteur(eleve.getNomTuteur().toUpperCase());
    	eleve.setNom(eleve.getNom().toUpperCase());
    	eleve.setPrenom(eleve.getPrenom().substring(0, 1).toUpperCase() + eleve.getPrenom().substring(1).toLowerCase());
    	eleve.setSolde(eleve.getSolde()-eleve.getInscription()-eleve.getRelicat()-eleve.getScolarite());
        return eleveRepository.save(eleve);
    }

    private String generateMatricule(Eleve eleve) {
		// TODO Auto-generated method stub
		return generatorService.generateMatricule(eleve);
	}

	private String generateCle(Eleve eleve) {
		
		String code1 = eleve.getClasse().getNom().substring(eleve.getClasse().getNom().length() - 4);
		String code2 = eleve.getGenre().equals("Fille") ? "F" : "G";
		String code3=eleve.getClasse().getNiveau().getLibelle();
		String code4=eleve.getNom().substring(0, 1).toUpperCase();
		String code5=eleve.getPrenom().substring(0, 1).toUpperCase();
		
		String result = code1+code2+code3+code4+code5;
		return result;
	}

	public Eleve updateEleve(Long id, Eleve eleveDetails) {
        Eleve eleve = eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Eleve non trouvée pour id : " + id));
        
        eleve.setNom(eleveDetails.getNom());
        eleve.setPrenom(eleveDetails.getPrenom());
        eleve.setNomTuteur(eleveDetails.getNomTuteur());
        
        eleve.setCle(generateCle(eleve));
    	eleve.setMatricule(generateMatricule(eleve));
        
        eleve.setNom(eleve.getNom().toUpperCase());
        eleve.setNomTuteur(eleve.getNomTuteur().toUpperCase());
   	    eleve.setPrenom(eleve.getPrenom().substring(0, 1).toUpperCase() + eleve.getPrenom().substring(1).toLowerCase());
   	    
        eleve.setGenre(eleveDetails.getGenre());
        eleve.setDateNaissance(eleveDetails.getDateNaissance());
        eleve.setLieuNaissance(eleveDetails.getLieuNaissance());
        eleve.setNomTuteur(eleveDetails.getNomTuteur());
        eleve.setTelTuteur(eleveDetails.getTelTuteur());
        eleve.setModePaiement(eleveDetails.getModePaiement());
        
        eleve.setSolde(eleve.getSolde()-eleveDetails.getInscription()-eleveDetails.getRelicat()-eleveDetails.getScolarite()
        		+eleve.getInscription()+eleve.getRelicat()+eleve.getScolarite());
        
        eleve.setInscription(eleveDetails.getInscription());
        eleve.setScolarite(eleveDetails.getScolarite());
        eleve.setRelicat(eleveDetails.getRelicat());
        eleve.setMensualite(eleveDetails.getMensualite());
       
        eleve.setClasse(eleveDetails.getClasse());
        return eleveRepository.save(eleve);
    }

    public void deleteEleve(Long id) {
        eleveRepository.deleteById(id);
    }
    
    public Page<Eleve> search(String searchTerm,String annee, int page, int size, String sortBy, String sortDirection) {
       
        // Déterminer la direction de tri à partir du paramètre sortDirection
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        
        // Créer un objet Sort dynamique en utilisant les paramètres sortBy et direction
        Sort sort = Sort.by(direction, sortBy);

        // Créer un objet Pageable avec les informations de pagination et de tri
        Pageable pageable = PageRequest.of(page, size, sort);
        return eleveRepository.searchByKeywordInAllColumns(searchTerm,annee, pageable);
    }
    
 // Méthode planifiée pour exécuter à la fin de chaque mois
    @Transactional 
    @Scheduled(cron = "0 0 0 25 10-12,1-6 ?")
    //@Scheduled(cron = "0 0/1 * * * ?")
    public void MiseAjourSolde() {
        List<Eleve> eleves = eleveRepository.findAll();

        for (Eleve eleve : eleves) {
            eleve.appliquerMisAjour();
            eleveRepository.save(eleve);
        }
    }
    
    
    
    public List<Eleve> processExcelFile(MultipartFile file) throws IOException {
        List<Eleve> eleves = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(7);
            for (Row row : sheet) {
                if (row.getRowNum() < 3) {
                    continue; // Ignorer les 4 premieres lignes
                }
                             // Créer une nouvelle instance d'Eleve
                Eleve eleve = new Eleve();

                // Vérifier si la référence existe
                String nom = getCellValue(row.getCell(2));
                eleve.setNom(nom.toUpperCase());
                eleve.setPrenom(getCellValue(row.getCell(1)).substring(0, 1).toUpperCase() +(getCellValue(row.getCell(1)).substring(1).toLowerCase()));
                eleve.setGenre((getCellValue(row.getCell(3)).equals("F"))? "Fille":"Garçon");
                
                
                // Numéro principal et secondaire
                 Classe classe = classeRepository.findById(10L).get();
                if(classe==null) {
               	 continue;
                }
                eleve.setClasse(classe);
                eleve.setCle(generateCle(eleve));
                eleve.setMatricule(generateMatricule(eleve));
                eleve.setInscription(7500);
                eleve.setMensualite(10000);
                eleve.setRelicat(0);
                eleve.setSolde(eleve.getSolde()-eleve.getInscription()-eleve.getRelicat()-eleve.getScolarite());
                
                
                // Sauvegarder l'opération
                eleveRepository.save(eleve);
                eleves.add(eleve); // Ajouter l'opération à la liste
                
            }
        }

        return eleves;
    }

    // Méthode pour récupérer la valeur d'une cellule en fonction de son type
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        // Déterminer le type de cellule
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Gérer les dates et les nombres différemment si nécessaire
                if (DateUtil.isCellDateFormatted(cell)) {
                    // Si la cellule contient une date, vous pouvez la formater comme vous le souhaitez
                    return cell.getDateCellValue().toString(); // Vous pouvez ajuster le format de date
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Vous pouvez gérer les formules différemment si nécessaire
            default:
                return "";
        }
    }
}

