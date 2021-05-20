package us.ascendtech.rest.dto;

public class Term {

	private String term;
	private Double weight;

	public Term(String term, Double weight) {
		this.term = term;
		this.weight = weight;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}

