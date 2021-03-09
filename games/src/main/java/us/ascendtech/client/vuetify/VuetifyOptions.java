package us.ascendtech.client.vuetify;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public class VuetifyOptions {

	private VuetifyTheme theme;
	private VuetifyIcons icons;

	@JsOverlay
	public final VuetifyTheme getTheme() {
		return theme;
	}

	@JsOverlay
	public final void setTheme(VuetifyTheme theme) {
		this.theme = theme;
	}

	@JsOverlay
	public final VuetifyIcons getIcons() {
		return icons;
	}

	@JsOverlay
	public final void setIcons(VuetifyIcons icons) {
		this.icons = icons;
	}
}
