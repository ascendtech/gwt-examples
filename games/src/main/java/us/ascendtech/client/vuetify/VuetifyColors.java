package us.ascendtech.client.vuetify;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public class VuetifyColors {

	private String primary;

	private String secondary;

	private String accent;

	private String error;

	private String info;

	private String success;

	private String warning;

	@JsOverlay
	public final String getPrimary() {
		return primary;
	}

	@JsOverlay
	public final void setPrimary(String primary) {
		this.primary = primary;
	}

	@JsOverlay
	public final String getSecondary() {
		return secondary;
	}

	@JsOverlay
	public final void setSecondary(String secondary) {
		this.secondary = secondary;
	}

	@JsOverlay
	public final String getAccent() {
		return accent;
	}

	@JsOverlay
	public final void setAccent(String accent) {
		this.accent = accent;
	}

	@JsOverlay
	public final String getError() {
		return error;
	}

	@JsOverlay
	public final void setError(String error) {
		this.error = error;
	}

	@JsOverlay
	public final String getInfo() {
		return info;
	}

	@JsOverlay
	public final void setInfo(String info) {
		this.info = info;
	}

	@JsOverlay
	public final String getSuccess() {
		return success;
	}

	@JsOverlay
	public final void setSuccess(String success) {
		this.success = success;
	}

	@JsOverlay
	public final String getWarning() {
		return warning;
	}

	@JsOverlay
	public final void setWarning(String warning) {
		this.warning = warning;
	}
}
