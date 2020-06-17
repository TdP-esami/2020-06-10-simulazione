package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	//INPUT
	int days;
	//OUTPUT
	int pauses;
	//mappa giorno/attore scelto
	Map<Integer, Actor> interviewedActors;
	//MODEL
	Graph<Actor, DefaultWeightedEdge> graph;
	//uso una lista per poter prendere più facilmente un elemento casuale
	List<Actor> availableActors;
	
	public Simulator(int n, Graph<Actor, DefaultWeightedEdge> graph) {
		this.days = n;
		this.graph = graph;
	}
	
	public void init() {
		interviewedActors = new HashMap<Integer,Actor>();
		this.pauses = 0;
		this.availableActors = new ArrayList<Actor>(this.graph.vertexSet());
	}
	
	public void run() {
		
		for(int i = 1 ; i <= this.days; i++) {
			
			Random rand = new Random();

			if(i == 1 || !interviewedActors.containsKey(i-1)) {
				//primo giorno oppure giorno dopo una pausa -> scelgo casualmente
				 Actor actor = availableActors.get(rand.nextInt(availableActors.size()));
				 interviewedActors.put(i, actor);
				 //rimuovo l'attore scelto da quelli disponibili
				 availableActors.remove(actor);				 
				 System.out.println("[GIORNO " + i + "] - selezionato autore casualmente (" + actor.toString() + ")");
				 continue ;
			}
			
			if(i >= 3 && interviewedActors.containsKey(i-1) && interviewedActors.containsKey(i-2) 
					&& interviewedActors.get(i-1).gender.equals(interviewedActors.get(i-2).gender)) {
				//per due giorni di fila il produttore ha intervistato attori dello stesso genere -> con il 90% di probabilità pausa
				if(rand.nextFloat() <= 0.9) {
					this.pauses ++;
					System.out.println("[GIORNO " + i + "] - pausa!");
					continue ;
				}
			}
			
			//se arrivo a questo punto -> il produttore può (forse) farsi consigliare dall'ultimo intervistato
			
			if(rand.nextFloat() <= 0.6) {
				//scelgo ancora casualmente
				Actor actor = availableActors.get(rand.nextInt(availableActors.size()));
				interviewedActors.put(i, actor);
				//rimuovo l'attore scelto da quelli disponibili
				availableActors.remove(actor);		
				System.out.println("[GIORNO " + i + "] - selezionato autore casualmente (" + actor.toString() + ")");
				continue ;
			} else {
				//mi faccio consigliare
				Actor lastInterviewed = interviewedActors.get(i-1);
				Actor recommended = this.getRecommended(lastInterviewed);
				if(recommended == null || !availableActors.contains(recommended)) {
					//se l'attore non fornisce consigli, o se l'attore consigliato è già stato intervistato -> scelgo casualmente
					Actor actor = availableActors.get(rand.nextInt(availableActors.size()));
					interviewedActors.put(i, actor);
					//rimuovo l'attore scelto da quelli disponibili
					availableActors.remove(actor);		
					System.out.println("[GIORNO " + i + "] - selezionato autore casualmente (" + actor.toString() + ")");
					continue ;
				} else {
					interviewedActors.put(i, recommended);
					//rimuovo l'attore scelto da quelli disponibili
					availableActors.remove(recommended);	
					System.out.println("[GIORNO " + i + "] - selezionato autore consigliato (" + recommended.toString() + ")");
					continue ;
				}
			}
		}
	}

	private Actor getRecommended(Actor lastInterviewed) {
		Actor recommended = null;
		int weight = 0;
		
		for(Actor neighbor : Graphs.neighborListOf(this.graph, lastInterviewed)) {
			if(this.graph.getEdgeWeight(this.graph.getEdge(lastInterviewed, neighbor)) > weight) {
				recommended = neighbor;
				weight = (int) this.graph.getEdgeWeight(this.graph.getEdge(lastInterviewed, neighbor));
			}
		}
		
		return recommended;
	}
	
	
	public int getPauses() {
		return this.pauses;
	}
	
	public Collection<Actor> getInterviewedActors(){
		return this.interviewedActors.values();
	}
	
	
}
