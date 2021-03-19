package us.ascendtech.client.vuetify;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(namespace = JsPackage.GLOBAL, isNative = true, name = "Object")
public class VuetifyIcons {

	private String iconfont;

	@JsOverlay
	public final String getIconfont() {
		return iconfont;
	}

	@JsOverlay
	public final void setIconfont(String iconfont) {
		this.iconfont = iconfont;
	}
}
