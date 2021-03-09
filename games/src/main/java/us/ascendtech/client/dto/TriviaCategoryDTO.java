package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class TriviaCategoryDTO {
	private int id;
	private String name;

	@JsOverlay
	public final int getId() {
		return id;
	}

	@JsOverlay
	public final void setId(int id) {
		this.id = id;
	}

	@JsOverlay
	public final String getName() {
		return name;
	}

	@JsOverlay
	public final void setName(String name) {
		this.name = name;
	}
}
