package it.polito.tdp.meteo.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private int Cottimo; 
	private int Cmin; 
	
	private MeteoDAO meteoDAO; 
	
	public Model() {
		this.meteoDAO= new MeteoDAO(); 
	}

	// of course you can change the String output with what you think works best
	public Map<String, Double> getUmiditaMedia(int mese) {
		
		return this.meteoDAO.getUmidità(mese);
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	
	public boolean cerca(List<Rilevamento> percorso, int livello, int mese) {
		
		//caso terminale
		if (percorso.size() == 15 && Cmin<Cottimo) {
			return true; 
		}
		
		// RIFLETTI BENE E SVOLGI il punto 2
		
		//Pos ultima= percorso.get(percorso.size()-1);  //ultima posizione valida inserita
		List<Rilevamento> p1 = new LinkedList<>();  
		return false;
	}
	

}
