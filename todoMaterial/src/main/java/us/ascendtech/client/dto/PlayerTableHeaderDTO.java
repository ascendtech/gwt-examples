package us.ascendtech.client.dto;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;

import static jsinterop.annotations.JsPackage.GLOBAL;

@JsType(namespace = GLOBAL, name = "Object", isNative = true)
public class PlayerTableHeaderDTO {
	private String text;
	private String align;
	private boolean sortable;
	private String value;

	@JsOverlay
	public final String getText() {
		return text;
	}

	@JsOverlay
	public final void setText(String text) {
		this.text = text;
	}

	@JsOverlay
	public final String getAlign() {
		return align;
	}

	@JsOverlay
	public final void setAlign(String align) {
		this.align = align;
	}

	@JsOverlay
	public final boolean isSortable() {
		return sortable;
	}

	@JsOverlay
	public final void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	@JsOverlay
	public final String getValue() {
		return value;
	}

	@JsOverlay
	public final void setValue(String value) {
		this.value = value;
	}
}
