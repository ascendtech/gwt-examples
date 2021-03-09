package us.ascendtech.client.vuetify;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public class VuetifyTheme {

	private VuetifyThemes themes;

	@JsOverlay
	public final VuetifyThemes getThemes() {
		return themes;
	}

	@JsOverlay
	public final void setThemes(VuetifyThemes themes) {
		this.themes = themes;
	}
}
