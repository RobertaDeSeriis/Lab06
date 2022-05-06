package it.polito.tdp.meteo.model;

import java.util.ArrayList;
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
	private List<Citta> citta; 
	private List <Citta> migliore; 
	
	private MeteoDAO meteoDAO; 
	
	public Model() {
		this.meteoDAO= new MeteoDAO(); 
		citta= new LinkedList<Citta>(meteoDAO.getAllCitta());
	}

	// of course you can change the String output with what you think works best
	public Map<String, Double> getUmiditaMedia(int mese) {
		
		return this.meteoDAO.getUmidità(mese);
	}
	
	// of course you can change the String output with what you think works best
	public List<Citta> trovaSequenza(int mese) {
		List<Citta> parziale= new LinkedList<Citta>();
		migliore=new LinkedList<Citta>();
		
		for (Citta c: citta)
			c.setRilevamenti(meteoDAO.getAllRilevamentiLocalitaMese(mese, c));
		
	 
		cerca(parziale,0);
		return migliore;
	}
	
	private void cerca(List<Citta> parziale, int livello) {

		if (livello== NUMERO_GIORNI_TOTALI) {
			//caso terminale
			Double costo = calcolaCosto(parziale);
			//migliore.size()==0 non uguale a null, altrimenti out of band
			if (migliore.size() == 0 || costo < calcolaCosto(migliore)) {
				//System.out.format("%f %s\n", costo, parziale);
				migliore = new ArrayList<>(parziale);
			}
			//System.out.println(parziale);
		}else {
			//caso intermedio
			for (Citta prova: citta) {
				if (aggiuntaValida(prova,parziale)) {
					parziale.add(prova);
					cerca(parziale, livello+1);
					parziale.remove(parziale.size()-1);
				}
			}			
		}
	}
		
		// RIFLETTI BENE E SVOLGI il punto 2
		
		//Pos ultima= percorso.get(percorso.size()-1);  //ultima posizione valida inserita
		//List<Rilevamento> p1 = new LinkedList<>();  
		//return false;
	

	private boolean aggiuntaValida(Citta c, List<Citta> parziale) {
		//verifica giorni massimi
				//contiamo quante volte la città 'prova' era già apparsa nell'attuale lista costruita fin qui
				int conta = 0;
				for (Citta ci:parziale) {
					if (ci.equals(c))
						conta++; 
				}
				if (conta >=6)
					return false;

				// verifica dei giorni minimi
				if (parziale.size()==0) //primo giorno posso inserire qualsiasi città
						return true;
				if (parziale.size()==1 || parziale.size()==2) {
					//siamo al secondo o terzo giorno, non posso cambiare
					//quindi l'aggiunta è valida solo se la città di prova coincide con la sua precedente
					return parziale.get(parziale.size()-1).equals(c); 
				}
				//nel caso generale, se ho già passato i controlli sopra, non c'è nulla che mi vieta di rimanere nella stessa città
				//quindi per i giorni successivi ai primi tre posso sempre rimanere
				if (parziale.get(parziale.size()-1).equals(c))
					return true; 
				// se cambio città mi devo assicurare che nei tre giorni precedenti sono rimasto fermo 
				if (parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) 
				&& parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
					return true;

				return false;

	}

private double calcolaCosto(List<Citta> parziale) {
	double c=0.0; 
	
	
		for (int i=1; i<=15; i++) {
			//prendo la prima città di parziale e la sua umidità
			Citta cit= parziale.get(i-1);
			//rilevamenti ha le umidità nei diveri giorni
			double umi= cit.getRilevamenti().get(i-1).getUmidita();
			c+= umi; // incremento il parametro variabile
		
	}
		
		for (int i=2; i<=15; i++) {
			if (!parziale.get(i-1).equals(parziale.get(i-2)))
				c+=100;
	}
		return c;
	}

}
