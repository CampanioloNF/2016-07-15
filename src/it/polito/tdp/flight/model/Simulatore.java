package it.polito.tdp.flight.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private List<Airport> airports; 
	private Map<Airport, Integer> aggiornamenti;
	
	private Random rand;
	
	public void init(Graph<Airport, DefaultWeightedEdge> grafo, int k) {
	
		this.grafo = grafo;
		this.airports = new LinkedList<Airport>(grafo.vertexSet());
		
		
		rand =  new Random();
		
		//disponiamo in maniera casuale i turisti
		
		for(Airport air : airports)
			air.azzeraTuristi();
		
		for (int i = 0; i<k; i++)
			airports.get(rand.nextInt(airports.size())).arrivaTurista(1);
		
	}

	public void run() {
		
		//per 16 voli 
		
		for(int i=0; i<16 ; i++) {
			
			/*
			 * Devo comunque avere l'accortenza di aggiornare la posizione dei turisti
			 * simultaneamente alla fine del viaggio 
			 */
			
			this.aggiornamenti = new HashMap<Airport, Integer>();
			
			//per ogni aereporto
			for(Airport air : airports) {
				
				//se c'è almeno un turista
				if(Graphs.successorListOf(grafo, air).size()>0) {
				
				while(air.getTuristi()>0) {
					
					//viene estratto un successore
					
					//se può spostarsi
					
						
					Airport prox = Graphs.successorListOf(grafo, air).get(rand.nextInt(Graphs.successorListOf(grafo, air).size()));
					
					if(aggiornamenti.containsKey(prox)) {
						int prima = aggiornamenti.get(prox);
						aggiornamenti.put(prox, prima+1);
							
					}
					else	
						aggiornamenti.put(prox, 1);
					
					air.lasciaTurist();
					
					}
				}
				
			}
			
			//aggiorno tutti 
			if(!aggiornamenti.isEmpty()) {
				
			for(Airport air: aggiornamenti.keySet()) 		
					air.arrivaTurista(aggiornamenti.get(air));
			
			}
				
			
		}
		
	}

	public String result() {
		
		if(this.airports!=null) {
			
			Collections.sort(airports);
			
			String ris = "";
			
			for(Airport air : airports) {
			
				if(air.getTuristi()>0)
			    	ris+=air.getName()+"  turisti: "+air.getTuristi()+"\n";
			
			}
			return ris;
			
		}
		
		return null;
	}

}
