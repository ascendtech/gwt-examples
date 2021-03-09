package us.ascendtech.client.vuetify;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public class VuetifyThemes {

	private VuetifyColors dark;

	private VuetifyColors light;

	@JsOverlay
	public final VuetifyColors getDark() {
		return dark;
	}

	@JsOverlay
	public final void setDark(VuetifyColors dark) {
		this.dark = dark;
	}

	@JsOverlay
	public final VuetifyColors getLight() {
		return light;
	}

	@JsOverlay
	public final void setLight(VuetifyColors light) {
		this.light = light;
	}
}
