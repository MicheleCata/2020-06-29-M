package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Director> idMap;
	
	private List<Director> percorsoMigliore;
	private int sum=0;
	
	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<Integer,Director>();
		dao.listAllDirectors(idMap);
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
		
		for (Adiacenze a: dao.getAdiacenze(anno, idMap)) {
			Graphs.addEdgeWithVertices(grafo, a.getD1(), a.getD2(),a.getPeso());
		}
		System.out.format("Grafo creato con %d vertici e %d archi\n",
 				this.grafo.vertexSet().size(), this.grafo.edgeSet().size()); 
	}
	
	public int getNumVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Director> getRegisti(){
		List<Director> registi = new ArrayList<>(grafo.vertexSet());
		
		return registi;
	}
	
	public List<ArcoPeso> getVicini(Director d) {
		List<ArcoPeso> result = new ArrayList<>();
		for (Director d1: Graphs.neighborListOf(grafo, d)) {
			DefaultWeightedEdge e = grafo.getEdge(d, d1);
			result.add(new ArcoPeso(d1,(int) grafo.getEdgeWeight(e)));
		}
		Collections.sort(result);
		return result;
	}
	
	
	public List<Director> getPercorso(Director partenza, Integer c){
		
		this.percorsoMigliore=new ArrayList<>();
		List<Director> parziale = new ArrayList<>();
		parziale.add(partenza);
		cerca(c,parziale);
		
		return percorsoMigliore;
		
	}

	private void cerca(Integer c, List<Director> parziale) {
		
		// CASO TERMINALE
		if (sum>c) {
			return;
		}
		else if(parziale.size()>this.percorsoMigliore.size()) {
				this.percorsoMigliore= new ArrayList<>(parziale);
				return;
			}
		
		List<ArcoPeso> vicini = this.getVicini(parziale.get(parziale.size()-1));
		for (ArcoPeso p: vicini) {
			if(!parziale.contains(p.getD())) {
				sum= sum+ p.getPeso();
				if(sum<=c) {
					parziale.add(p.getD());
					cerca(c,parziale);
					parziale.remove(parziale.size()-1);
				}
			}
			
		}
		
		
	}
}
