package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;


import it.polito.tdp.flight.db.FlightDAO;

public class Model {

	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> airportIdMap;
	private Set<Route> rotte;
	private  ConnectivityInspector<Airport, DefaultWeightedEdge> ci;
	private FlightDAO dao;
	private Simulatore sim;
	
	/*
	 * In questo esame la situazione delle query è un po' complessa
	 * bisogna estrapolare dalle rotte gli aereoporti e considerare le loro distanze
	 * Forse risulta più opportuno caricare tutto direttamente nel programma che poi elabora i dati nella loro complessita'
	 */
	
	public Model() {
	
		this.dao = new FlightDAO();
		this.airportIdMap = new HashMap<Integer, Airport>();
		
		//nella mappa abbiamo tutti i possibili aereporti
		dao.getAllAirports(airportIdMap);
		//ci prendiamo anche tutte le possibili rotte
		this.rotte = new HashSet<Route>(dao.getAllRoutes());
		
		this.sim = new Simulatore();
		
	}
	
	public void creaGrafo(int distMax) {
		
		this.grafo = new SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//creiamo qua il grafo
		for(Route rotta : rotte) {
			
			if(airportIdMap.containsKey(rotta.getSourceAirportId()) && airportIdMap.containsKey(rotta.getDestinationAirportId())) {
				
				Airport source = airportIdMap.get(rotta.getSourceAirportId());
				Airport destination = airportIdMap.get(rotta.getDestinationAirportId());
				
				if(!grafo.containsVertex(source))
					grafo.addVertex(source);
				if(!grafo.containsVertex(destination))
					grafo.addVertex(destination);
				
				double distanza = LatLngTool.distance(source.getCoord(), destination.getCoord(), LengthUnit.KILOMETER);
				
				// se la distanza tra gli aereoporti è minore di quella massima
				if(distanza<=distMax) {
				   
					double peso = (double)((distanza*60)/800) ; //in minuti
					Graphs.addEdge(grafo, source, destination, peso);
					
				}
				
			}
			
		}
	
		
	}

	public boolean isConnected() {
		
		if(grafo!=null) {
			
			 ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
			  
			 return ci.isConnected();
			
		}
		
		return false;
	}

	public Airport getAirport() {
		
		/*
		 * La posso pensare in questo modo.
		 * Per ogni Airport connesso a Fiumicino mi calcolo il peso con l'algoritmo di Dijkstra 
		 * e il peso più alto corrisponde al nodo più distante
		 */
		
		
		if(grafo!=null) {
			
		    
			 Airport partenza = null;
		
			 for(Airport air : airportIdMap.values()) {
				 if(air.getName().equals("Fiumicino")) {
					 partenza = air;
					 break;
				 }
			 }
			 
			 if(partenza == null) {
				 System.out.println("Problema con Fiumicino");
				 return null;
			 }
			 
			 //avendo il riferimento all'aereoporto di partenza
				 
			 DijkstraShortestPath<Airport, DefaultWeightedEdge> dijkstra = 
					 new DijkstraShortestPath<>(grafo);
			 
			 ci = new ConnectivityInspector<Airport, DefaultWeightedEdge>(grafo);
			 
			 double peso = 0;
			 Airport ris = null;
			 for(Airport air : ci.connectedSetOf(partenza)) {
				 if(dijkstra.getPathWeight(partenza, air)>peso) {
					 ris = air;
					 peso = dijkstra.getPathWeight(partenza, air);
						
				 }
			 }
			 
			 return ris;
			 
		}
		
		return null;
	}

	public String simulate(int k) {
		
		if(grafo!=null) {
		
		sim.init(grafo, k);
		sim.run();
	  
		return sim.result();
	
		}
		return null;
	}
	
	
}
