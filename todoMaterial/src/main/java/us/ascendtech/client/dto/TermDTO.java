package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class TermDTO {

	private String term;
	private Double weight;

	@JsOverlay
	public final String getTerm() {
		return term;
	}

	@JsOverlay
	public final void setTerm(String term) {
		this.term = term;
	}

	@JsOverlay
	public final Double getWeight() {
		return weight;
	}

	@JsOverlay
	public final void setWeight(Double weight) {
		this.weight = weight;
	}
}
