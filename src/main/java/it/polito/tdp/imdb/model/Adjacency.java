package it.polito.tdp.imdb.model;

public class Adjacency {
	Actor a1;
	Actor a2;
	Integer weight;
	
	public Adjacency(Actor a1, Actor a2, Integer weight) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.weight = weight;
	}
	
	public Actor getA1() {
		return a1;
	}
	public void setA1(Actor a1) {
		this.a1 = a1;
	}
	public Actor getA2() {
		return a2;
	}
	public void setA2(Actor a2) {
		this.a2 = a2;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
