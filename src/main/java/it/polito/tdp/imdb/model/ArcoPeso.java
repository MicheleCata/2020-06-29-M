package it.polito.tdp.imdb.model;

public class ArcoPeso implements Comparable <ArcoPeso>{
	
	private Director d;
	private Integer peso;
	
	
	public ArcoPeso(Director d, Integer peso) {
		super();
		this.d = d;
		this.peso = peso;
	}

	public Director getD() {
		return d;
	}

	public Integer getPeso() {
		return peso;
	}

	@Override
	public int compareTo(ArcoPeso other) {
		
		return -this.getPeso()-(other.getPeso());
	}
	
	public String toString() {
		return this.d.getFirstName()+" "+this.d.lastName+" "+ this.peso;
	}
	
	
	

}
