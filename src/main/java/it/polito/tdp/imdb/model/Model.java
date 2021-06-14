package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private SimpleWeightedGraph<Director, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Director> idMap;
	
	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<Integer,Director>();
		dao.listAllDirectors(idMap);
	}
	
	public void creaGrafo(int anno) {
		grafo = new SimpleWeightedGraph<Director, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getVertici(anno, idMap));
	}
}
